package com.almland.carworkshop.infrastructure.adaptor.inbound.rest.dto.response;

import java.time.LocalDateTime;

public class TimeSlotResponseDTO {

    private LocalDateTime startTime;
    private LocalDateTime endTime;

    private TimeSlotResponseDTO(Builder builder) {
        startTime = builder.startTime;
        endTime = builder.endTime;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public static final class Builder {
        private LocalDateTime startTime;
        private LocalDateTime endTime;

        public Builder() {
        }

        public Builder startTime(LocalDateTime val) {
            startTime = val;
            return this;
        }

        public Builder endTime(LocalDateTime val) {
            endTime = val;
            return this;
        }

        public TimeSlotResponseDTO build() {
            return new TimeSlotResponseDTO(this);
        }
    }
}
