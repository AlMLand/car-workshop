package com.almland.carworkshop.infrastructure.adaptor.inbound.rest.dto.request;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class AppointmentRequestDTO {
    private LocalDateTime start;
    private String workShopOffer;
}
