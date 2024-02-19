package com.almland.carworkshop.domain;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

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
}
