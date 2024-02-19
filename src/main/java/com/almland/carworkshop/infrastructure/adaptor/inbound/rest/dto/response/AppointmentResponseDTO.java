package com.almland.carworkshop.infrastructure.adaptor.inbound.rest.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
public class AppointmentResponseDTO {
    private UUID workShopId;
    private LocalDateTime start;
    private LocalDateTime end;
    private String workShopOffer;
}
