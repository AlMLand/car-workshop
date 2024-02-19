package com.almland.carworkshop.infrastructure.adaptor.inbound.rest.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class TimeSlotResponseDTO {
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}
