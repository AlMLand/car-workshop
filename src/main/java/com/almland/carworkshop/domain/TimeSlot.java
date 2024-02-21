package com.almland.carworkshop.domain;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
@EqualsAndHashCode
public class TimeSlot {

    private UUID timeSlotId;
    private LocalDateTime startTime;
    @Setter
    private LocalDateTime endTime;

    public LocalDate getStartTimeLocalDate() {
        return startTime.toLocalDate();
    }

    public boolean isOverlapping(TimeSlot overlappingTimeSlot) {
        return isTimeSlotEndInOverlappingInterval(this, overlappingTimeSlot) ||
                isTimeSlotEndEqualOverlappingIntervalEnd(this, overlappingTimeSlot) ||
                isTimeSlotStartInOverlappingInterval(this, overlappingTimeSlot) ||
                isTimeSlotStartEqualOverlappingStart(this, overlappingTimeSlot) ||
                isTimeSlotAroundOverlappingInterval(this, overlappingTimeSlot);
    }

    private boolean isTimeSlotAroundOverlappingInterval(TimeSlot timeSlot, TimeSlot overlappingTimeSlot) {
        return overlappingTimeSlot.getStartTime().isAfter(timeSlot.getStartTime()) &&
                overlappingTimeSlot.getEndTime().isBefore(timeSlot.getEndTime());
    }

    private boolean isTimeSlotStartEqualOverlappingStart(TimeSlot timeSlot, TimeSlot overlappingTimeSlot) {
        return timeSlot.getStartTime().isEqual(overlappingTimeSlot.getStartTime());
    }

    private boolean isTimeSlotStartInOverlappingInterval(TimeSlot timeSlot, TimeSlot overlappingTimeSlot) {
        return timeSlot.getStartTime().isAfter(overlappingTimeSlot.getStartTime()) &&
                timeSlot.getStartTime().isBefore(overlappingTimeSlot.getEndTime());
    }

    private boolean isTimeSlotEndEqualOverlappingIntervalEnd(TimeSlot timeSlot, TimeSlot overlappingTimeSlot) {
        return timeSlot.getEndTime().isEqual(overlappingTimeSlot.getEndTime());
    }

    private boolean isTimeSlotEndInOverlappingInterval(TimeSlot timeSlot, TimeSlot overlappingTimeSlot) {
        return timeSlot.getEndTime().isAfter(overlappingTimeSlot.getStartTime()) &&
                timeSlot.getEndTime().isBefore(overlappingTimeSlot.getEndTime());
    }
}
