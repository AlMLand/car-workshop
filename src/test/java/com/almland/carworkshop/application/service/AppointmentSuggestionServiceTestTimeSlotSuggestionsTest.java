package com.almland.carworkshop.application.service;

import com.almland.carworkshop.domain.TimeSlot;
import com.almland.carworkshop.domain.WorkShopOffer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static com.almland.carworkshop.domain.Offer.MOT;
import static java.time.Month.FEBRUARY;
import static org.assertj.core.api.Assertions.assertThat;

public class AppointmentSuggestionServiceTestTimeSlotSuggestionsTest {
    public static final int COUNT_OF_ALL_SUGGESTIONS_ON_DAY_WITHOUT_OVERLAPPING_INTERVALS = 52;
    private AppointmentSuggestionService appointmentSuggestionService;
    private WorkShopOffer workShopOffer;
    private List<TimeSlot> overlappingIntervals;
    private Set<LocalDateTime> possibleStartTimesOnOneDay;
    private Set<LocalDateTime> possibleStartTimesOnTheeDays;

    @BeforeEach
    public void setUp() {
        appointmentSuggestionService = new AppointmentSuggestionService();
        workShopOffer = WorkShopOffer.builder()
                .workShopOfferId(UUID.randomUUID())
                .offer(MOT)
                .durationInMin(240)
                .build();
        overlappingIntervals = List.of(
                TimeSlot.builder()
                        .startTime(LocalDateTime.of(2024, FEBRUARY, 18, 9, 30, 0))
                        .endTime(LocalDateTime.of(2024, FEBRUARY, 18, 10, 30, 0))
                        .build(),
                TimeSlot.builder()
                        .startTime(LocalDateTime.of(2024, FEBRUARY, 18, 13, 0, 0))
                        .endTime(LocalDateTime.of(2024, FEBRUARY, 18, 16, 0, 0))
                        .build(),
                TimeSlot.builder()
                        .startTime(LocalDateTime.of(2024, FEBRUARY, 19, 8, 15, 0))
                        .endTime(LocalDateTime.of(2024, FEBRUARY, 19, 9, 15, 0))
                        .build(),
                TimeSlot.builder()
                        .startTime(LocalDateTime.of(2024, FEBRUARY, 19, 11, 15, 0))
                        .endTime(LocalDateTime.of(2024, FEBRUARY, 19, 11, 30, 0))
                        .build()
        );
    }

    @Test
    public void test1() {
        possibleStartTimesOnOneDay = appointmentSuggestionService.getPossibleStartTimes(
                List.of(LocalDate.of(2024, FEBRUARY, 18))
        );
        var localDates = possibleStartTimesOnOneDay.stream().map(LocalDateTime::toLocalDate).distinct().toList();
        assertThat(localDates.size()).isEqualTo(1);
        assertThat(localDates.get(0)).isEqualTo(LocalDate.of(2024, FEBRUARY, 18));

        var timeSlotSuggestions = appointmentSuggestionService
                .getTimeSlotSuggestions(workShopOffer, possibleStartTimesOnOneDay, overlappingIntervals);

        assertThat(timeSlotSuggestions.size()).isNotEqualTo(COUNT_OF_ALL_SUGGESTIONS_ON_DAY_WITHOUT_OVERLAPPING_INTERVALS);
        assertThat(timeSlotSuggestions.size()).isEqualTo(20);
    }

    @Test
    public void test2() {
        possibleStartTimesOnTheeDays = appointmentSuggestionService.getPossibleStartTimes(
                List.of(
                        LocalDate.of(2024, FEBRUARY, 18),
                        LocalDate.of(2024, FEBRUARY, 19)
                )
        );
        var localDates = possibleStartTimesOnTheeDays.stream().map(LocalDateTime::toLocalDate).distinct().toList();
        assertThat(localDates.size()).isEqualTo(2);
        assertThat(localDates)
                .containsOnly(LocalDate.of(2024, FEBRUARY, 18), LocalDate.of(2024, FEBRUARY, 19));

        var timeSlotSuggestions = appointmentSuggestionService
                .getTimeSlotSuggestions(workShopOffer, possibleStartTimesOnTheeDays, overlappingIntervals);

        assertThat(timeSlotSuggestions.size()).isNotEqualTo(COUNT_OF_ALL_SUGGESTIONS_ON_DAY_WITHOUT_OVERLAPPING_INTERVALS * 2);
        assertThat(timeSlotSuggestions.size()).isEqualTo(58);
    }
}
