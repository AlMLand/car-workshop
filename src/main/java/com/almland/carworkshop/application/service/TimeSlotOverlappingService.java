package com.almland.carworkshop.application.service;

import com.almland.carworkshop.domain.TimeSlot;
import org.apache.commons.lang3.NotImplementedException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TimeSlotOverlappingService {

    public List<TimeSlot> findOverlaps(List<TimeSlot> availableTimeSlots, int maxParallelAppointments) {
        return switch (maxParallelAppointments) {
            case 2 -> findOverlapsByMaxTwo(availableTimeSlots);
            case 3 -> findOverlapsByMaxThree(availableTimeSlots);
            default -> throw new NotImplementedException("this functionality is yet not implemented");
        };
    }

    private List<TimeSlot> findOverlapsByMaxTwo(List<TimeSlot> timeSlots) {
        var overlaps = new ArrayList<TimeSlot>();

        for (int index1 = 0; index1 < timeSlots.size(); index1++) {
            var timeSlot1 = timeSlots.get(index1);
            for (int index2 = index1 + 1; index2 < timeSlots.size(); index2++) {
                var timeSlot2 = timeSlots.get(index2);
                var overlapStart = timeSlot1.getStartTime().isAfter(timeSlot2.getStartTime()) ?
                        timeSlot1.getStartTime() :
                        timeSlot2.getStartTime();
                var overlapEnd = timeSlot1.getEndTime().isBefore(timeSlot2.getEndTime()) ?
                        timeSlot1.getEndTime() :
                        timeSlot2.getEndTime();

                addOverlappingTimeSlot(overlapStart, overlapEnd, overlaps);
            }
        }
        return overlaps;
    }

    private List<TimeSlot> findOverlapsByMaxThree(List<TimeSlot> timeSlots) {
        var overlaps = new ArrayList<TimeSlot>();

        for (int index1 = 0; index1 < timeSlots.size(); index1++) {
            var timeSlot1 = timeSlots.get(index1);
            for (int index2 = index1 + 1; index2 < timeSlots.size(); index2++) {
                var timeslot2 = timeSlots.get(index2);
                for (int index3 = index2 + 1; index3 < timeSlots.size(); index3++) {
                    var timeSlot3 = timeSlots.get(index3);
                    var overlapStart = timeSlot1.getStartTime().isAfter(timeslot2.getStartTime()) ?
                            timeSlot1.getStartTime() :
                            timeslot2.getStartTime();
                    overlapStart = overlapStart.isAfter(timeSlot3.getStartTime()) ?
                            overlapStart :
                            timeSlot3.getStartTime();

                    var overlapEnd = timeSlot1.getEndTime().isBefore(timeslot2.getEndTime()) ?
                            timeSlot1.getEndTime() :
                            timeslot2.getEndTime();
                    overlapEnd = overlapEnd.isBefore(timeSlot3.getEndTime()) ?
                            overlapEnd :
                            timeSlot3.getEndTime();

                    addOverlappingTimeSlot(overlapStart, overlapEnd, overlaps);
                }
            }
        }
        return overlaps;
    }

    private void addOverlappingTimeSlot(
            LocalDateTime overlapStart,
            LocalDateTime overlapEnd,
            ArrayList<TimeSlot> overlaps
    ) {
        if (overlapStart.isBefore(overlapEnd)) {
            overlaps.add(TimeSlot.builder()
                    .startTime(overlapStart)
                    .endTime(overlapEnd)
                    .build());
        }
    }
}
