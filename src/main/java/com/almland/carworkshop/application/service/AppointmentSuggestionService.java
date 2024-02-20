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
import java.util.function.Function;
import java.util.function.Supplier;
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
            int maxParallelAppointments
    ) {
        var availableTimeSlots = getAvailableTimeSlots(availableAppointments);
        var dates = getAvailableDates(availableTimeSlots);
        var allPossibleStartTimes = getPossibleStartTimes(dates);
        var overlappingIntervals = findOverlaps(availableTimeSlots, maxParallelAppointments);
        var timeSlotSuggestions = getTimeSlotSuggestions(workShopOffer, allPossibleStartTimes, overlappingIntervals);
        return mapToAppointment(workShopId, workShopOfferId, timeSlotSuggestions);
    }

    private List<TimeSlot> findOverlaps(List<TimeSlot> availableTimeSlots, int maxParallelAppointments) {
        return switch (maxParallelAppointments) {
            case 2 -> findOverlapsByMaxTwo(availableTimeSlots);
            case 3 -> findOverlapsByMaxThree(availableTimeSlots);
            default -> throw new NotImplementedException("this functionality is yet not implemented");
        };
    }

    private Set<Appointment> mapToAppointment(
            UUID workShopId,
            UUID workShopOfferId,
            List<TimeSlot> timeSlotSuggestions
    ) {
        return timeSlotSuggestions.stream()
                .map(timeSlot -> Appointment.builder()
                        .workShop(WorkShop.builder()
                                .workShopId(workShopId)
                                .build())
                        .workShopOffer(WorkShopOffer.builder()
                                .workShopOfferId(workShopOfferId)
                                .build())
                        .timeSlot(TimeSlot.builder()
                                .startTime(timeSlot.getStartTime())
                                .endTime(timeSlot.getEndTime())
                                .build())
                        .build()
                )
                .collect(Collectors.toSet());
    }

    public List<TimeSlot> getTimeSlotSuggestions(
            WorkShopOffer workShopOffer,
            Set<LocalDateTime> allPossibleStartTimes,
            List<TimeSlot> overlappingIntervals
    ) {
        return allPossibleStartTimes.stream()
                .map(startedTime -> TimeSlot.builder()
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

    boolean isOverlapping(TimeSlot timeSlot, TimeSlot overlappingTimeSlot) {
        return isTimeSlotEndInOverlappingInterval(timeSlot, overlappingTimeSlot) ||
                isTimeSlotEndEqualOverlappingIntervalEnd(timeSlot, overlappingTimeSlot) ||
                isTimeSlotStartInOverlappingInterval(timeSlot, overlappingTimeSlot) ||
                isTimeSlotStartEqualOverlappingStart(timeSlot, overlappingTimeSlot) ||
                isTimeSlotAroundOverlappingInterval(timeSlot, overlappingTimeSlot);
    }

    private static boolean isTimeSlotAroundOverlappingInterval(TimeSlot timeSlot, TimeSlot overlappingTimeSlot) {
        return overlappingTimeSlot.getStartTime().isAfter(timeSlot.getStartTime()) && overlappingTimeSlot.getEndTime().isBefore(timeSlot.getEndTime());
    }

    private static boolean isTimeSlotStartEqualOverlappingStart(TimeSlot timeSlot, TimeSlot overlappingTimeSlot) {
        return timeSlot.getStartTime().isEqual(overlappingTimeSlot.getStartTime());
    }

    private static boolean isTimeSlotStartInOverlappingInterval(TimeSlot timeSlot, TimeSlot overlappingTimeSlot) {
        return timeSlot.getStartTime().isAfter(overlappingTimeSlot.getStartTime()) && timeSlot.getStartTime().isBefore(overlappingTimeSlot.getEndTime());
    }

    private static boolean isTimeSlotEndEqualOverlappingIntervalEnd(TimeSlot timeSlot, TimeSlot overlappingTimeSlot) {
        return timeSlot.getEndTime().isEqual(overlappingTimeSlot.getEndTime());
    }

    private static boolean isTimeSlotEndInOverlappingInterval(TimeSlot timeSlot, TimeSlot overlappingTimeSlot) {
        return timeSlot.getEndTime().isAfter(overlappingTimeSlot.getStartTime()) && timeSlot.getEndTime().isBefore(overlappingTimeSlot.getEndTime());
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
                        overlaps.add(TimeSlot.builder()
                                .startTime(overlapStart)
                                .endTime(overlapEnd)
                                .build());
                    }
                }
            }
        }
        return overlaps;
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
                    overlaps.add(TimeSlot.builder()
                            .startTime(overlapStart)
                            .endTime(overlapEnd)
                            .build());
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

    public Set<LocalDateTime> getPossibleStartTimes(List<LocalDate> dates) {
        return dates.stream()
                .map(date -> HOURS.stream()
                        .map(hour -> MINUTES.stream()
                                .map(minute -> LocalDateTime.of(date, hour.plusMinutes(minute)))
                        )
                )
                .reduce(Stream::concat)
                .orElseGet(getActualDate())
                .flatMap(Function.identity())
                .collect(Collectors.toSet());
    }

    private Supplier<Stream<Stream<LocalDateTime>>> getActualDate() {
        return () -> Set.of(LocalDate.now()).stream()
                .flatMap(date -> HOURS.stream()
                        .map(hour -> MINUTES.stream()
                                .map(minute -> LocalDateTime.of(date, hour.plusMinutes(minute)))
                        )
                );
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
}
