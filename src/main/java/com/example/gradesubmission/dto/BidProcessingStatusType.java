
package com.example.gradesubmission.dto;

import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class BidProcessingStatusType {
    
    private Date submissionTime;
    
    private String transactionId;
    
    private List<MessageType> message;
    
}
