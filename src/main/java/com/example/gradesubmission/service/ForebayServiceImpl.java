package com.example.gradesubmission.service;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.example.gradesubmission.dto.ActAsMarketParticipantType;
import com.example.gradesubmission.dto.ForebayBidQuery;
import com.example.gradesubmission.dto.ForebayBidQueryType;
import com.example.gradesubmission.dto.ForebayBidSetType;
import com.example.gradesubmission.dto.ForebayBidSubmit;
import com.example.gradesubmission.dto.ForebayBidSubmitType;
import com.example.gradesubmission.dto.LinkedForebayType;
import com.example.gradesubmission.dto.QueryDateType;
import com.example.gradesubmission.dto.QueryStandingType;
import com.example.gradesubmission.dto.UploadDateType;
import com.example.gradesubmission.dto.UploadStandingType;
import com.example.gradesubmission.entity.Forebay;
import com.example.gradesubmission.repository.ForebayRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class ForebayServiceImpl implements ForebayService {

    private static final Logger LOG = LoggerFactory.getLogger(ForebayServiceImpl.class);

    private ForebayRepository forebayRepository;

    public ForebayBidSetType retrieveForebay(ForebayBidQuery forebayQuery) {

        ForebayBidQueryType forebayQueryType = forebayQuery.getForebayBidQuery();
        ActAsMarketParticipantType actAs = forebayQueryType.getActAsMarketParticipant();

        String participantName = actAs.getParticipant();
        String userName = actAs.getUser();
        String forebayName = forebayQueryType.getForebayName();

        QueryDateType queryDate = forebayQueryType.getDate();
        Date deliveryDate = queryDate.getDeliveryDate();

        QueryStandingType queryStanding = queryDate.getStanding();
        char isStanding = queryStanding != null ? 'T' : 'F';
        String standingBidDay = queryStanding != null ? queryStanding.getDayOfWeek().toString() : "NA";
        Date expiryDate = queryStanding != null ? queryStanding.getExpiryDate() : null;

        LOG.info("{}, {}, {}, {}, {}", participantName, userName, isStanding, expiryDate, standingBidDay);
        java.sql.Date sqlDate = new java.sql.Date(deliveryDate.getTime());

        try {
            Optional<Forebay> optionalForebay = forebayRepository
                    .findByParticipantIdAndForebayNameAndDeliveryDateAndStandingFlagAndStandingBidDay(
                            participantName, forebayName, sqlDate, isStanding, standingBidDay);

            ForebayBidSetType forebayBidSetType = new ForebayBidSetType();

            if (optionalForebay.isPresent()) {
                Forebay forebay = optionalForebay.get();
                LOG.info("Retrieved {}", forebay.getForebayName());

                ForebayBidSubmitType bid = createForebayBidSubmitType(forebay);
                LOG.info("Bid: {}", bid);

                List<ForebayBidSubmitType> bids = Collections.singletonList(bid);
                forebayBidSetType.setForebayBidSubmit(bids);
                LOG.info("ForebayBidSetType: {}", forebayBidSetType);
            }

            return forebayBidSetType;

        } catch (Exception e) {
            LOG.error("Error retrieving forebay data: {}", e.getMessage());
        }
        return null;

    }

    // Refactor repeated code into a method.
    private ForebayBidSubmitType createForebayBidSubmitType(Forebay forebay) {
        ForebayBidSubmitType bid = new ForebayBidSubmitType();
        setKeyQueryResultValues(bid, forebay);
        setBodyQueryResultValues(bid, forebay);
        return bid;
    }

    /**
     * Method to set query result header level elements values
     *
     * @param forebaySubmitType
     * @param forebayHb
     * @param procInfo
     */
    public void setKeyQueryResultValues(
            ForebayBidSubmitType forebaySubmitType,
            Forebay forebayHb) {

        ActAsMarketParticipantType actAsMktPartType = new ActAsMarketParticipantType();
        actAsMktPartType.setParticipant(forebayHb.getParticipantId());
        actAsMktPartType.setUser("UNAME");
        forebaySubmitType.setActAsMarketParticipant(actAsMktPartType);

        forebaySubmitType.setDate(new UploadDateType());
        if (forebayHb.getStandingFlag() == 'Y') {
            UploadStandingType standingType = new UploadStandingType();
            standingType.setExpiryDate(new java.sql.Date(0));
            standingType.setDayOfWeek(QueryStandingType.DayOfWeekType.valueOf(forebayHb.getStandingBidDay()));
            forebaySubmitType.getDate().setStanding(standingType);
        } else {
            forebaySubmitType.getDate().setDeliveryDate(
                    forebayHb.getDeliveryDate());
        }

        forebaySubmitType.setForebayName(forebayHb.getForebayName());
    }

    /**
     * Method to set query result body elements
     *
     * @param forebaySubmitType
     * @param forebayHb
     * @param procInfo
     * @throws QueryFault
     */
    public void setBodyQueryResultValues(ForebayBidSubmitType forebaySubmitType,
            Forebay forebayHb) {

        forebaySubmitType.setMinDailyEnergyLimit(forebayHb.getMinDailyEngyLimit());
        forebaySubmitType.setMaxDailyEnergyLimit(forebayHb.getMaxDailyEngyLimit());

        forebaySubmitType.setMaxDailyEnergyLimitReason(
                forebayHb.getMaxDlyReasonCode());

        forebaySubmitType.setReason(forebayHb.getReasonCode());

        forebaySubmitType.setOtherReason(
                forebayHb.getOtherReason());
        setLinkedForebayQueryResultValues(forebaySubmitType, forebayHb);
    }

    /**
     * Sets the linked forebay query result values based on the given Forebay
     * object.
     * 
     * @param forebaySubmitType the object where the result will be set
     * @param forebayHb         the Forebay object containing the data
     */
    private void setLinkedForebayQueryResultValues(ForebayBidSubmitType forebaySubmitType, Forebay forebayHb) {

        Optional.ofNullable(forebayHb.getLinkedDownForebayFlag())
                .filter(Boolean.TRUE::equals)
                .ifPresent(flag -> createAndSetLinkedForebay(forebaySubmitType, forebayHb));
    }

    private void createAndSetLinkedForebay(ForebayBidSubmitType forebaySubmitType, Forebay forebayHb) {
        if (forebayHb.getForebayNameDown() != null && forebayHb.getTimeLag() != null
                && forebayHb.getMwRatio() != null) {
            LinkedForebayType ref = new LinkedForebayType();

            ref.setDownstreamForebayName(forebayHb.getForebayNameDown());
            ref.setTimeLag(forebayHb.getTimeLag());
            ref.setMwRatio(forebayHb.getMwRatio());

            forebaySubmitType.setLinkedForebay(ref);
        } else {
            // Log warning or handle the case where one or more properties are null
        }
    }

    public boolean persistForebay(ForebayBidSubmit forebaySubmit) {
        ForebayBidSubmitType forebay = forebaySubmit.getForebayBidSubmit();

        ActAsMarketParticipantType actAs = forebay.getActAsMarketParticipant();
        String participantName = actAs.getParticipant();

        Date deliveryDate = forebay.getDate().getDeliveryDate();
        UploadDateType queryDate = forebay.getDate();
        UploadStandingType uploadStanding = queryDate.getStanding();

        char isStanding = uploadStanding != null ? 'T' : 'F';
        String standingBidDay = uploadStanding != null ? uploadStanding.getDayOfWeek().toString() : "NA";
        Date expiryDate = uploadStanding != null ? uploadStanding.getExpiryDate() : null;

        String forebayName = forebay.getForebayName();

        Forebay forebayHb = new Forebay();
        forebayHb.setExpiryDate(expiryDate);
        forebayHb.setBidStatus("I");

        setKeyDbFieldsForInsert(forebayHb, participantName, deliveryDate,
                standingBidDay, isStanding, forebayName);
        setNonKeyDbFieldsForInsertOrUpdate(forebayHb, forebay);

        return true;
    }

    private void setKeyDbFieldsForInsert(Forebay forebayHb,
            String participantId, Date deliveryDate,
            String dayOfWeek, Character standingFlag,
            String forebayName) {
        forebayHb.setParticipantId(participantId);
        forebayHb.setDeliveryDate(deliveryDate);
        forebayHb.setStandingBidDay(dayOfWeek);
        forebayHb.setStandingFlag(standingFlag);
        forebayHb.setForebayName(forebayName);
    }

    /**
     * @param forebayHb
     * @param forebayBidType
     */
    private void setNonKeyDbFieldsForInsertOrUpdate(Forebay forebayHb,
            ForebayBidSubmitType forebay) {

        forebayHb.setSubmitId(111111L);
        forebayHb.setOriginFlag("N");
        forebayHb.setRowStatus("N");
        forebayHb.setWindowState("W");
        forebayHb.setTransactionId("TTTTTTT");
        forebayHb.setMinDailyEngyLimit(forebay.getMinDailyEnergyLimit());
        forebayHb.setMaxDailyEngyLimit(forebay.getMaxDailyEnergyLimit());
        forebayHb.setMaxDlyReasonCode(forebay.getMaxDailyEnergyLimitReason());
        LinkedForebayType linkedForebayType = forebay.getLinkedForebay();
        if (linkedForebayType != null) {
            forebayHb.setLinkedDownForebayFlag(true);
            forebayHb.setForebayNameDown(
                    linkedForebayType.getDownstreamForebayName());
            forebayHb.setTimeLag(linkedForebayType.getTimeLag());
            forebayHb.setMwRatio(linkedForebayType.getMwRatio());
        } else {
            forebayHb.setLinkedDownForebayFlag(false);
            forebayHb.setForebayNameDown(null);
            forebayHb.setTimeLag(null);
            forebayHb.setMwRatio(null);
        }

        forebayHb.setReasonCode(forebay.getReason());
        forebayHb.setOtherReason(forebay.getOtherReason());
        forebayHb.setForebaySequenceId(2L);
        forebayHb.setCascadeGroupName("GROUP");
        forebayHb.setForebaySequenceIddown(3L);
        forebayRepository.save(forebayHb);

    }

}
