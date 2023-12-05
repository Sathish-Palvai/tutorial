package com.example.gradesubmission.entity;

import java.math.BigDecimal;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Forebay", uniqueConstraints = {
        @UniqueConstraint(columnNames = { "PARTICIPANT_ID", "FOREBAY_NAME",
            "DELIVERY_DATE", "STANDING_FLAG",
            "STANDING_BID_DAY" }) })
public class Forebay {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false, length = 18)
    private long id;

    @Column(name = "PARTICIPANT_ID", nullable = false, length = 12)
    private String participantId;

    @Column(name = "FOREBAY_NAME", nullable = false, length = 32)
    private String forebayName;

    @Column(name = "DELIVERY_DATE", nullable = false)
    private Date deliveryDate;

    @Column(name = "STANDING_FLAG", nullable = false, length = 1)
    private Character standingFlag;

    @Column(name = "STANDING_BID_DAY", nullable = false, length = 3)
    private String standingBidDay;

    @Column(name = "TRANSACTION_ID", nullable = false, length = 10)
    private String transactionId;

    @Column(name = "SUBMIT_ID", nullable = false, length = 18)
    private Long submitId;

    @Column(name = "ORIGIN_FLAG", nullable = false, length = 1)
    private String originFlag;

    @Column(name = "EXPIRY_DATE")
    private Date expiryDate;

    @Column(name = "REASON_CODE", length = 12)
    private String reasonCode;

    @Column(name = "OTHER_REASON", length = 128)
    private String otherReason;

    @Column(name = "OPERATOR_REASON", length = 128)
    private String operatorReason;

    @Column(name = "WINDOW_STATE", nullable = false, length = 5)
    private String windowState;

    @Column(name = "FOREBAY_SEQUENCE_ID", nullable = false, length = 13)
    private Long forebaySequenceId;

    @Column(name = "LINKED_DOWN_FOREBAY_FLAG", nullable = false, length = 1)
    private Boolean linkedDownForebayFlag;

    @Column(name = "FOREBAY_NAME_DOWN", length = 32)
    private String forebayNameDown;

    @Column(name = "FOREBAY_SEQUENCE_IDDOWN", length = 13)
    private Long forebaySequenceIddown;

    @Column(name = "TIME_LAG", length = 4)
    private Integer timeLag;

    @Column(name = "MW_RATIO")
    private BigDecimal mwRatio;

    @Column(name = "MIN_DAILY_ENGY_LIMIT")
    private BigDecimal minDailyEngyLimit;

    @Column(name = "MAX_DAILY_ENGY_LIMIT")
    private BigDecimal maxDailyEngyLimit;

    @Column(name = "MAX_DLY_REASON_CODE", length = 12)
    private String maxDlyReasonCode;

    @Column(name = "BID_STATUS", nullable = false, length = 1)
    private String bidStatus;

    @Column(name = "UPDATED_BY", length = 12)
    private String updatedBy;

    @Column(name = "UPDATED_DATE")
    private Date updatedDate;

    @Column(name = "ROW_STATUS", nullable = false, length = 1)
    private String rowStatus;

    @Column(name = "CASCADE_GROUP_NAME", nullable = false, length = 32)
    private String cascadeGroupName;

}
