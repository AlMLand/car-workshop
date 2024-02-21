package com.almland.carworkshop.application.service;

import com.almland.carworkshop.domain.TimeSlot;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static java.time.Month.FEBRUARY;
import static org.assertj.core.api.Assertions.assertThat;

public class TimeSlotOverlappingServiceIsOverlappingTest {

    private TimeSlot valideTimeSlot;
    private TimeSlot overlappingTimeSlot;
    private TimeSlot timeSlotEndIsOverlapping;
    private TimeSlot timeSlotStartIsOverlapping;
    private TimeSlot overlappingTimeSlotInTimeSlot;
    private TimeSlot timeSlotInOverlappingTimeSlot;

    @BeforeEach
    public void setUp() {
        overlappingTimeSlot = TimeSlot.builder()
                .startTime(LocalDateTime.of(2024, FEBRUARY, 1, 9, 30, 0))
                .endTime(LocalDateTime.of(2024, FEBRUARY, 1, 10, 30, 0))
                .build();

        timeSlotEndIsOverlapping = TimeSlot.builder()
                .startTime(LocalDateTime.of(2024, FEBRUARY, 1, 9, 0, 0))
                .endTime(LocalDateTime.of(2024, FEBRUARY, 1, 10, 0, 0))
                .build();

        timeSlotStartIsOverlapping = TimeSlot.builder()
                .startTime(LocalDateTime.of(2024, FEBRUARY, 1, 10, 0, 0))
                .endTime(LocalDateTime.of(2024, FEBRUARY, 1, 11, 0, 0))
                .build();

        overlappingTimeSlotInTimeSlot = TimeSlot.builder()
                .startTime(LocalDateTime.of(2024, FEBRUARY, 1, 9, 0, 0))
                .endTime(LocalDateTime.of(2024, FEBRUARY, 1, 11, 0, 0))
                .build();
        timeSlotInOverlappingTimeSlot = TimeSlot.builder()
                .startTime(LocalDateTime.of(2024, FEBRUARY, 1, 9, 45, 0))
                .endTime(LocalDateTime.of(2024, FEBRUARY, 1, 10, 15, 0))
                .build();
        valideTimeSlot = TimeSlot.builder()
                .startTime(LocalDateTime.of(2024, FEBRUARY, 1, 9, 0, 0))
                .endTime(LocalDateTime.of(2024, FEBRUARY, 1, 9, 30, 0))
                .build();
    }

    @Test
    public void timeSlotEndIsOverlapping() {
        assertThat(timeSlotEndIsOverlapping.isOverlapping(overlappingTimeSlot)).isTrue();
    }

    @Test
    public void timeSlotStartIsOverlapping() {
        assertThat(timeSlotStartIsOverlapping.isOverlapping(overlappingTimeSlot)).isTrue();
    }

    @Test
    public void overlappingTimeSlotInTimeSlot() {
        assertThat(overlappingTimeSlotInTimeSlot.isOverlapping(overlappingTimeSlot)).isTrue();
    }

    @Test
    public void timeSlotInOverlappingTimeSlot() {
        assertThat(timeSlotInOverlappingTimeSlot.isOverlapping(overlappingTimeSlot)).isTrue();
    }

    @Test
    public void valideTimeSlot() {
        assertThat(valideTimeSlot.isOverlapping(overlappingTimeSlot)).isFalse();
    }
}
