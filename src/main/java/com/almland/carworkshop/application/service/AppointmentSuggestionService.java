package com.almland.carworkshop.application.service;

import com.almland.carworkshop.domain.Appointment;
import com.almland.carworkshop.domain.TimeSlot;
import com.almland.carworkshop.domain.WorkShop;
import com.almland.carworkshop.domain.WorkShopOffer;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class AppointmentSuggestionService {

    private TimeSlotOverlappingService timeSlotOverlappingService;

    public AppointmentSuggestionService(TimeSlotOverlappingService timeSlotOverlappingService) {
        this.timeSlotOverlappingService = timeSlotOverlappingService;
    }

    public Set<Appointment> getAllAppointmentSuggestions(
            WorkShopOffer workShopOffer,
            Set<Appointment> availableAppointments,
            UUID workShopId, UUID workShopOfferId,
            int maxParallelAppointments
    ) {
        var availableTimeSlots = getAvailableTimeSlots(availableAppointments);
        var allPossibleStartTimes = getAllPossibleStartTimes(availableAppointments);
        var overlappingIntervals = timeSlotOverlappingService.findOverlaps(availableTimeSlots, maxParallelAppointments);
        var timeSlotSuggestions = getTimeSlotSuggestions(workShopOffer, allPossibleStartTimes, overlappingIntervals);
        return mapToAppointment(workShopId, workShopOfferId, timeSlotSuggestions);
    }

    private Set<LocalDateTime> getAllPossibleStartTimes(Set<Appointment> availableAppointments) {
        return availableAppointments.stream()
                .flatMap(appointment -> appointment.getPossibleStartTime().stream())
                .collect(Collectors.toSet());
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
                .filter(timeSlot -> overlappingIntervals.stream().noneMatch(timeSlot::isOverlapping))
                .sorted(Comparator.comparing(TimeSlot::getStartTime))
                .toList();
    }

    private List<TimeSlot> getAvailableTimeSlots(Set<Appointment> availableAppointments) {
        return availableAppointments.stream()
                .map(Appointment::getTimeSlot)
                .sorted(Comparator.comparing(TimeSlot::getStartTime))
                .toList();
    }
}
