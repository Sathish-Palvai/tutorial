
package com.example.gradesubmission.dto;

import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QueryStandingType {

    private QueryStandingType.DayOfWeekType dayOfWeek;
    private Date expiryDate;

    public QueryStandingType.DayOfWeekType getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(QueryStandingType.DayOfWeekType dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public enum DayOfWeekType {

        MON("MON"),
        TUE("TUE"),
        WED("WED"),
        THU("THU"),
        FRI("FRI"),
        SAT("SAT"),
        SUN("SUN"),
        ALL("ALL");

        private final String value;
        private static final Map<String, QueryStandingType.DayOfWeekType> CONSTANTS = new HashMap<String, QueryStandingType.DayOfWeekType>();

        static {
            for (QueryStandingType.DayOfWeekType c : values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        DayOfWeekType(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return this.value;
        }

        @JsonValue
        public String value() {
            return this.value;
        }

        @JsonCreator
        public static QueryStandingType.DayOfWeekType fromValue(String value) {
            QueryStandingType.DayOfWeekType constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }

}
