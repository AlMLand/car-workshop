package com.almland.carworkshop.domain;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public class TimeSlot {

    private UUID timeSlotId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TimeSlot timeSlot = (TimeSlot) o;
        return Objects.equals(timeSlotId, timeSlot.timeSlotId) &&
                Objects.equals(startTime, timeSlot.startTime) &&
                Objects.equals(endTime, timeSlot.endTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(timeSlotId, startTime, endTime);
    }

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
