package com.almland.carworkshop.domain;

import java.time.LocalDateTime;
import java.util.UUID;

public class TimeSlot {

    private UUID timeSlotId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    private TimeSlot(Builder builder) {
        timeSlotId = builder.timeSlotId;
        startTime = builder.startTime;
        endTime = builder.endTime;
    }

    public UUID getTimeSlotId() {
        return timeSlotId;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public static final class Builder {
        private UUID timeSlotId;
        private LocalDateTime startTime;
        private LocalDateTime endTime;

        public Builder() {
        }

        public Builder timeSlotId(UUID val) {
            timeSlotId = val;
            return this;
        }

        public Builder startTime(LocalDateTime val) {
            startTime = val;
            return this;
        }

        public Builder endTime(LocalDateTime val) {
            endTime = val;
            return this;
        }

        public TimeSlot build() {
            return new TimeSlot(this);
        }
    }
}
