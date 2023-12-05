
package com.example.gradesubmission.dto;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class LinkedForebayType {

    private String downstreamForebayName;

    private Integer timeLag;

    private BigDecimal mwRatio;

}
