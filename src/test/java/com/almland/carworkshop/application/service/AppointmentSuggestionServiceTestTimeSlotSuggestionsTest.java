package com.almland.carworkshop.application.service;

import com.almland.carworkshop.domain.Appointment;
import com.almland.carworkshop.domain.TimeSlot;
import com.almland.carworkshop.domain.WorkShopOffer;
import com.almland.carworkshop.utils.PostgreSqlTestConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.almland.carworkshop.domain.Offer.MOT;
import static java.time.Month.FEBRUARY;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Import(value = PostgreSqlTestConfiguration.class)
public class AppointmentSuggestionServiceTestTimeSlotSuggestionsTest {
    public static final int COUNT_OF_ALL_SUGGESTIONS_ON_DAY_WITHOUT_OVERLAPPING_INTERVALS = 52;
    @Autowired
    private AppointmentSuggestionService appointmentSuggestionService;
    private WorkShopOffer workShopOffer;
    private List<TimeSlot> overlappingIntervals;
    private Appointment appointment;
    private Set<Appointment> appointments;

    @BeforeEach
    public void setUp() {
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
    public void appointmentWithPossibleStartTimesOnOneDay() {
        var initLocalDate = LocalDate.of(2024, FEBRUARY, 18);
        appointment = Appointment.builder()
                .timeSlot(TimeSlot.builder()
                        .startTime(LocalDateTime.of(initLocalDate, LocalTime.now()))
                        .build())
                .build();
        var localDate = appointment.getTimeSlot().getStartTimeLocalDate();
        assertThat(localDate).isEqualTo(initLocalDate);

        var timeSlotSuggestions = appointmentSuggestionService.getTimeSlotSuggestions(
                workShopOffer,
                appointment.getPossibleStartTime(),
                overlappingIntervals
        );

        assertThat(timeSlotSuggestions.size()).isNotEqualTo(COUNT_OF_ALL_SUGGESTIONS_ON_DAY_WITHOUT_OVERLAPPING_INTERVALS);
        assertThat(timeSlotSuggestions.size()).isEqualTo(20);
    }

    @Test
    public void appointmentsWithPossibleStartTimesOnTheeDays() {
        var initLocalDate1 = LocalDate.of(2024, FEBRUARY, 18);
        var initLocalDate2 = LocalDate.of(2024, FEBRUARY, 19);
        appointments = Set.of(
                Appointment.builder()
                        .timeSlot(TimeSlot.builder()
                                .startTime(LocalDateTime.of(initLocalDate1, LocalTime.now()))
                                .build())
                        .build(),
                Appointment.builder()
                        .timeSlot(TimeSlot.builder()
                                .startTime(LocalDateTime.of(initLocalDate2, LocalTime.now()))
                                .build())
                        .build()
        );
        var localDates = appointments.stream()
                .map(appointment -> appointment.getTimeSlot().getStartTimeLocalDate())
                .distinct()
                .toList();
        assertThat(localDates.size()).isEqualTo(2);
        assertThat(localDates).containsOnly(initLocalDate1, initLocalDate2);

        var timeSlotSuggestions = appointmentSuggestionService.getTimeSlotSuggestions(
                workShopOffer,
                appointments.stream()
                        .flatMap(appointment -> appointment.getPossibleStartTime().stream())
                        .collect(Collectors.toSet()),
                overlappingIntervals
        );

        assertThat(timeSlotSuggestions.size()).isNotEqualTo(COUNT_OF_ALL_SUGGESTIONS_ON_DAY_WITHOUT_OVERLAPPING_INTERVALS * 2);
        assertThat(timeSlotSuggestions.size()).isEqualTo(58);
    }
}
