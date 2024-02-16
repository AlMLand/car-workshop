package com.almland.carworkshop.domain;

import java.time.LocalDateTime;
import java.util.UUID;

public class TimeSlot {
    private UUID timeSlotId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public TimeSlot(UUID timeSlotId, LocalDateTime startTime, LocalDateTime endTime) {
        this.timeSlotId = timeSlotId;
        this.startTime = startTime;
        this.endTime = endTime;
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
}
