package com.almland.carworkshop.infrastructure.adaptor.inbound.rest.dto;

import java.time.LocalDateTime;

public class AppointmentRestDTO {
    LocalDateTime start;
    LocalDateTime end;
    String workShopOffer;

    private AppointmentRestDTO(Builder builder) {
        start = builder.start;
        end = builder.end;
        workShopOffer = builder.workShopOffer;
    }

    public static final class Builder {
        private final LocalDateTime start;
        private final LocalDateTime end;
        private final String workShopOffer;

        public Builder(LocalDateTime start, LocalDateTime end, String workShopOffer) {
            this.start = start;
            this.end = end;
            this.workShopOffer = workShopOffer;
        }

        public AppointmentRestDTO build() {
            return new AppointmentRestDTO(this);
        }
    }
}
