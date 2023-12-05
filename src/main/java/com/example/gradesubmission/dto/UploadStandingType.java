
package com.example.gradesubmission.dto;

import java.sql.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UploadStandingType {

    private QueryStandingType.DayOfWeekType dayOfWeek;
   
    private Date expiryDate;

}
