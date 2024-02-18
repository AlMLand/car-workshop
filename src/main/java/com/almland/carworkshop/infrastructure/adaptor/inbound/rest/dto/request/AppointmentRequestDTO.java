package com.almland.carworkshop.infrastructure.adaptor.inbound.rest.dto.request;

import java.time.LocalDateTime;

public class AppointmentRequestDTO {
    private LocalDateTime start;
    private String workShopOffer;

    public LocalDateTime getStart() {
        return start;
    }

    public String getWorkShopOffer() {
        return workShopOffer;
    }
}
