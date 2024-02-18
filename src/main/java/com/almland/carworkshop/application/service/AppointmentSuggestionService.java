package com.almland.carworkshop.application.service;

import com.almland.carworkshop.domain.Appointment;
import com.almland.carworkshop.domain.TimeSlot;
import com.almland.carworkshop.domain.WorkShop;
import com.almland.carworkshop.domain.WorkShopOffer;
import org.apache.commons.lang3.NotImplementedException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AppointmentSuggestionService {

    private final static List<LocalTime> HOURS = List.of(
            LocalTime.of(8, 0),
            LocalTime.of(9, 0),
            LocalTime.of(10, 0),
            LocalTime.of(11, 0),
            LocalTime.of(12, 0),
            LocalTime.of(13, 0),
            LocalTime.of(14, 0),
            LocalTime.of(15, 0),
            LocalTime.of(16, 0),
            LocalTime.of(17, 0),
            LocalTime.of(18, 0),
            LocalTime.of(19, 0),
            LocalTime.of(20, 0)
    );
    private final static List<Integer> MINUTES = List.of(0, 15, 30, 45);

    public Set<Appointment> getAllAppointmentSuggestions(
            WorkShopOffer workShopOffer,
            Set<Appointment> availableAppointments,
            UUID workShopId, UUID workShopOfferId,
            int maxParallelAppointments) {
        var availableTimeSlots = getAvailableTimeSlots(availableAppointments);
        var dates = getAvailableDates(availableTimeSlots);
        var allPossibleStartTimes = getPossibleStartTimes(dates);
        var overlappingIntervals = findOverlaps(availableTimeSlots, maxParallelAppointments);
        var timeSlotSuggestions = getTimeSlotSuggestions(workShopOffer, allPossibleStartTimes, overlappingIntervals);
        return mapToAppointment(workShopId, workShopOfferId, timeSlotSuggestions);
    }

    private List<TimeSlot> findOverlaps(List<TimeSlot> availableTimeSlots, int maxParallelAppointments) {
        if (maxParallelAppointments == 2) return findOverlapsByMaxTwo(availableTimeSlots);
        else if (maxParallelAppointments == 3) return findOverlapsByMaxThree(availableTimeSlots);
        else throw new NotImplementedException("this functionality is yet not implemented");
    }

    private Set<Appointment> mapToAppointment(
            UUID workShopId,
            UUID workShopOfferId,
            List<TimeSlot> timeSlotSuggestions
    ) {
        return timeSlotSuggestions.stream()
                .map(timeSlot -> new Appointment.Builder()
                        .workShop(new WorkShop.Builder()
                                .workShopId(workShopId)
                                .build())
                        .workShopOffer(new WorkShopOffer.Builder()
                                .workShopOfferId(workShopOfferId)
                                .build())
                        .timeSlot(new TimeSlot.Builder()
                                .startTime(timeSlot.getStartTime())
                                .endTime(timeSlot.getEndTime())
                                .build())
                        .build()
                )
                .collect(Collectors.toSet());
    }

    private List<TimeSlot> getTimeSlotSuggestions(
            WorkShopOffer workShopOffer,
            Set<LocalDateTime> allPossibleStartTimes,
            List<TimeSlot> overlappingIntervals
    ) {
        return allPossibleStartTimes.stream()
                .map(startedTime -> new TimeSlot.Builder()
                        .startTime(startedTime)
                        .endTime(startedTime.plusMinutes(workShopOffer.getDurationInMin()))
                        .build()
                )
                .filter(timeSlot ->
                        overlappingIntervals.stream().noneMatch(overlappingTimeSlot ->
                                isOverlapping(timeSlot, overlappingTimeSlot)
                        )
                )
                .sorted(Comparator.comparing(TimeSlot::getStartTime))
                .toList();
    }

    private boolean isOverlapping(TimeSlot timeSlot, TimeSlot overlappingTimeSlot) {
        return ((isTimeSlotStartEqualOverlappingStart(timeSlot, overlappingTimeSlot) ||
                isTimeSlotStartAfterAfterOverlappingStart(timeSlot, overlappingTimeSlot)) &&
                isTimeSlotStartBeforeOverlappingEnd(timeSlot, overlappingTimeSlot)) ||
                ((isTimeSlotEndEqualOverlappingEnd(timeSlot, overlappingTimeSlot) ||
                        isTimeSlotEndBeforeOverlappingEnd(timeSlot, overlappingTimeSlot)) &&
                        isTimeSlotEndAfterOverlappingStart(timeSlot, overlappingTimeSlot));
    }

    private boolean isTimeSlotEndAfterOverlappingStart(TimeSlot timeSlot, TimeSlot overlappingTimeSlot) {
        return isFirstStartAfterSecondStart(timeSlot.getEndTime(), overlappingTimeSlot);
    }

    private boolean isTimeSlotEndBeforeOverlappingEnd(TimeSlot timeSlot, TimeSlot overlappingTimeSlot) {
        return isFirstEndBeforeSecondEnd(timeSlot, overlappingTimeSlot);
    }

    private boolean isTimeSlotEndEqualOverlappingEnd(TimeSlot timeSlot, TimeSlot overlappingTimeSlot) {
        return timeSlot.getEndTime().isEqual(overlappingTimeSlot.getEndTime());
    }

    private boolean isTimeSlotStartBeforeOverlappingEnd(TimeSlot timeSlot, TimeSlot overlappingTimeSlot) {
        return isFirstEndBeforeSecondEnd(timeSlot.getStartTime(), overlappingTimeSlot);
    }

    private boolean isTimeSlotStartAfterAfterOverlappingStart(TimeSlot timeSlot, TimeSlot overlappingTimeSlot) {
        return isFirstStartAfterSecondStart(timeSlot, overlappingTimeSlot);
    }

    private boolean isTimeSlotStartEqualOverlappingStart(TimeSlot timeSlot, TimeSlot overlappingTimeSlot) {
        return timeSlot.getStartTime().isEqual(overlappingTimeSlot.getStartTime());
    }

    public List<TimeSlot> findOverlapsByMaxThree(List<TimeSlot> timeSlots) {
        var overlaps = new ArrayList<TimeSlot>();

        for (int index1 = 0; index1 < timeSlots.size(); index1++) {
            var timeSlot1 = timeSlots.get(index1);
            for (int index2 = index1 + 1; index2 < timeSlots.size(); index2++) {
                var timeslot2 = timeSlots.get(index2);
                for (int index3 = index2 + 1; index3 < timeSlots.size(); index3++) {
                    var timeSlot3 = timeSlots.get(index3);
                    var overlapStart = isFirstStartAfterSecondStart(timeSlot1, timeslot2) ?
                            timeSlot1.getStartTime() :
                            timeslot2.getStartTime();
                    overlapStart = isFirstStartAfterSecondStart(overlapStart, timeSlot3) ?
                            overlapStart :
                            timeSlot3.getStartTime();

                    var overlapEnd = isFirstEndBeforeSecondEnd(timeSlot1, timeslot2) ?
                            timeSlot1.getEndTime() :
                            timeslot2.getEndTime();
                    overlapEnd = isFirstEndBeforeSecondEnd(overlapEnd, timeSlot3) ?
                            overlapEnd :
                            timeSlot3.getEndTime();

                    if (overlapStart.isBefore(overlapEnd)) {
                        overlaps.add(new TimeSlot.Builder()
                                .startTime(overlapStart)
                                .endTime(overlapEnd)
                                .build());
                    }
                }
            }
        }
        return overlaps;
    }

    private boolean isFirstEndBeforeSecondEnd(LocalDateTime overlapEnd, TimeSlot timeSlot3) {
        return overlapEnd.isBefore(timeSlot3.getEndTime());
    }

    private boolean isFirstStartAfterSecondStart(LocalDateTime overlapStart, TimeSlot timeSlot3) {
        return overlapStart.isAfter(timeSlot3.getStartTime());
    }

    private boolean isFirstEndBeforeSecondEnd(TimeSlot timeSlot1, TimeSlot timeslot2) {
        return isFirstEndBeforeSecondEnd(timeSlot1.getEndTime(), timeslot2);
    }

    private boolean isFirstStartAfterSecondStart(TimeSlot timeSlot1, TimeSlot timeslot2) {
        return isFirstStartAfterSecondStart(timeSlot1.getStartTime(), timeslot2);
    }

    private List<TimeSlot> findOverlapsByMaxTwo(List<TimeSlot> timeSlots) {
        var overlaps = new ArrayList<TimeSlot>();

        for (int index1 = 0; index1 < timeSlots.size(); index1++) {
            var timeSlot1 = timeSlots.get(index1);
            for (int index2 = index1 + 1; index2 < timeSlots.size(); index2++) {
                var timeSlot2 = timeSlots.get(index2);
                var overlapStart = isFirstStartAfterSecondStart(timeSlot1, timeSlot2) ?
                        timeSlot1.getStartTime() :
                        timeSlot2.getStartTime();
                var overlapEnd = isFirstEndBeforeSecondEnd(timeSlot1, timeSlot2) ?
                        timeSlot1.getEndTime() :
                        timeSlot2.getEndTime();

                if (overlapStart.isBefore(overlapEnd)) {
                    overlaps.add(new TimeSlot.Builder()
                            .startTime(overlapStart)
                            .endTime(overlapEnd)
                            .build());
                }
            }
        }
        return overlaps;
    }

    private Set<LocalDateTime> getPossibleStartTimes(List<LocalDate> dates) {
        return dates.stream()
                .map(date -> HOURS.stream()
                        .map(hour -> MINUTES.stream()
                                .map(minute -> LocalDateTime.of(date, hour.plusMinutes(minute)))
                        )
                )
                .reduce(Stream::concat)
                .orElseThrow()
                .reduce(Stream::concat)
                .orElseThrow()
                .collect(Collectors.toSet());
    }

    private List<LocalDate> getAvailableDates(List<TimeSlot> takenTimeSlots) {
        return takenTimeSlots.stream()
                .map(timeSlot -> timeSlot.getStartTime().toLocalDate())
                .distinct()
                .toList();
    }

    private List<TimeSlot> getAvailableTimeSlots(Set<Appointment> availableAppointments) {
        return availableAppointments.stream()
                .map(Appointment::getTimeSlot)
                .sorted(Comparator.comparing(TimeSlot::getStartTime))
                .toList();
    }

    public boolean isNewAppointmentOverlapping(
            int maxParallelAppointments,
            Set<Appointment> availableAppointments,
            Appointment appointment
    ) {
        var availableTimeSlots = getAvailableTimeSlots(availableAppointments);
        var overlappingIntervals = findOverlaps(availableTimeSlots, maxParallelAppointments);
        return overlappingIntervals.stream().noneMatch(overlappingTimeSlot ->
                isOverlapping(appointment.getTimeSlot(), overlappingTimeSlot)
        );
    }
}
