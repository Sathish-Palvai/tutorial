
package com.example.gradesubmission.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UploadDateType {

    private Date deliveryDate;

    @JsonInclude(Include.NON_NULL)
    private UploadStandingType standing;

}
