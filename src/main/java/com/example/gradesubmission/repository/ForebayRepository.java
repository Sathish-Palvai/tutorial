package com.example.gradesubmission.repository;

import java.sql.Date;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.example.gradesubmission.entity.Forebay;

public interface ForebayRepository extends CrudRepository<Forebay, Long> {

    Optional<Forebay> findByParticipantIdAndForebayNameAndDeliveryDateAndStandingFlagAndStandingBidDay(
            String participantId, String forebayName, Date deliveryDate, Character standingFlag,
            String standingBidDay);

}
