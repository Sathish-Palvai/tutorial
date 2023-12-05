
package com.example.gradesubmission.dto;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ForebayBidSubmitType {

    private ActAsMarketParticipantType actAsMarketParticipant;

    private UploadDateType date;

    private String forebayName;

    @JsonInclude(Include.NON_NULL)
    private LinkedForebayType linkedForebay;

    private BigDecimal minDailyEnergyLimit;

    private BigDecimal maxDailyEnergyLimit;
    private String maxDailyEnergyLimitReason;
    private String reason;
    private String otherReason;

}
