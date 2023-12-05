
package com.example.gradesubmission.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ForebayBidQueryType {

    private ActAsMarketParticipantType actAsMarketParticipant;

    private QueryDateType date;

    private String forebayName;

}
