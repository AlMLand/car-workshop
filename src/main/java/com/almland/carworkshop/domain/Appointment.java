package com.almland.carworkshop.domain;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
@Builder
@EqualsAndHashCode
public class Appointment {

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

    private UUID appointmentId;
    private WorkShop workShop;
    private TimeSlot timeSlot;
    private WorkShopOffer workShopOffer;

    public Set<LocalDateTime> getPossibleStartTime() {
        return HOURS.stream()
                .map(hour -> MINUTES.stream()
                        .map(minute -> LocalDateTime.of(timeSlot.getStartTimeLocalDate(), hour.plusMinutes(minute)))
                )
                .reduce(Stream::concat)
                .orElseGet(getActualDate())
                .collect(Collectors.toSet());
    }

    private Supplier<Stream<LocalDateTime>> getActualDate() {
        return () -> HOURS.stream()
                .map(hour ->
                        MINUTES.stream().map(minute -> LocalDateTime.of(LocalDate.now(), hour.plusMinutes(minute)))
                )
                .reduce(Stream::concat)
                .orElseThrow();
    }
}
