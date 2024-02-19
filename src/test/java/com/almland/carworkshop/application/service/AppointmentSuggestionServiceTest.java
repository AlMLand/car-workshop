package com.almland.carworkshop.application.service;

import com.almland.carworkshop.domain.TimeSlot;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static java.time.Month.FEBRUARY;
import static org.assertj.core.api.Assertions.assertThat;

public class AppointmentSuggestionServiceTest {

    private AppointmentSuggestionService appointmentSuggestionService;
    private TimeSlot valideTimeSlot;
    private TimeSlot overlappingTimeSlot;
    private TimeSlot timeSlotEndIsOverlapping;
    private TimeSlot timeSlotStartIsOverlapping;
    private TimeSlot overlappingTimeSlotInTimeSlot;
    private TimeSlot timeSlotInOverlappingTimeSlot;

    @BeforeEach
    public void setUp() {
        appointmentSuggestionService = new AppointmentSuggestionService();
        overlappingTimeSlot = TimeSlot.builder()
                .startTime(LocalDateTime.of(2024, FEBRUARY, 1, 9, 30, 00))
                .endTime(LocalDateTime.of(2024, FEBRUARY, 1, 10, 30, 00))
                .build();

        timeSlotEndIsOverlapping = TimeSlot.builder()
                .startTime(LocalDateTime.of(2024, FEBRUARY, 1, 9, 0, 00))
                .endTime(LocalDateTime.of(2024, FEBRUARY, 1, 10, 0, 00))
                .build();

        timeSlotStartIsOverlapping = TimeSlot.builder()
                .startTime(LocalDateTime.of(2024, FEBRUARY, 1, 10, 0, 00))
                .endTime(LocalDateTime.of(2024, FEBRUARY, 1, 11, 0, 00))
                .build();

        overlappingTimeSlotInTimeSlot = TimeSlot.builder()
                .startTime(LocalDateTime.of(2024, FEBRUARY, 1, 9, 0, 00))
                .endTime(LocalDateTime.of(2024, FEBRUARY, 1, 11, 0, 00))
                .build();
        timeSlotInOverlappingTimeSlot = TimeSlot.builder()
                .startTime(LocalDateTime.of(2024, FEBRUARY, 1, 9, 45, 00))
                .endTime(LocalDateTime.of(2024, FEBRUARY, 1, 10, 15, 00))
                .build();
        valideTimeSlot = TimeSlot.builder()
                .startTime(LocalDateTime.of(2024, FEBRUARY, 1, 9, 0, 00))
                .endTime(LocalDateTime.of(2024, FEBRUARY, 1, 9, 30, 00))
                .build();
    }

    @Test
    public void timeSlotEndIsOverlapping() {
        assertThat(appointmentSuggestionService.isOverlapping(timeSlotEndIsOverlapping, overlappingTimeSlot))
                .isTrue();
    }

    @Test
    public void timeSlotStartIsOverlapping() {
        assertThat(appointmentSuggestionService.isOverlapping(timeSlotStartIsOverlapping, overlappingTimeSlot))
                .isTrue();
    }

    @Test
    public void overlappingTimeSlotInTimeSlot() {
        assertThat(appointmentSuggestionService.isOverlapping(overlappingTimeSlotInTimeSlot, overlappingTimeSlot))
                .isTrue();
    }

    @Test
    public void timeSlotInOverlappingTimeSlot() {
        assertThat(appointmentSuggestionService.isOverlapping(timeSlotInOverlappingTimeSlot, overlappingTimeSlot))
                .isTrue();
    }

    @Test
    public void valideTimeSlot() {
        assertThat(appointmentSuggestionService.isOverlapping(valideTimeSlot, overlappingTimeSlot))
                .isFalse();
    }
}
