
package com.example.gradesubmission.dto;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MessageType {

    private MessageType.SeverityType severity;

    private String code;

    private String description;

    private Integer hour;

    public Integer getHour() {
        return hour;
    }

    public void setHour(Integer hour) {
        this.hour = hour;
    }

    public enum SeverityType {

        INFO("INFO"),
        WARN("WARN"),
        ERROR("ERROR");

        private final String value;
        private final static Map<String, MessageType.SeverityType> CONSTANTS = new HashMap<String, MessageType.SeverityType>();

        static {
            for (MessageType.SeverityType c : values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        SeverityType(String value) {
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
        public static MessageType.SeverityType fromValue(String value) {
            MessageType.SeverityType constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }

}
