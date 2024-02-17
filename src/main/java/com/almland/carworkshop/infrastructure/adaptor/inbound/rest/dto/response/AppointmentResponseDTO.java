package com.almland.carworkshop.infrastructure.adaptor.inbound.rest.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;

public class AppointmentResponseDTO {
    private UUID workShopId;
    private LocalDateTime start;
    private LocalDateTime end;
    private String workShopOffer;

    private AppointmentResponseDTO(Builder builder) {
        workShopId = builder.workShopId;
        start = builder.start;
        end = builder.end;
        workShopOffer = builder.workShopOffer;
    }


    public static final class Builder {
        private UUID workShopId;
        private LocalDateTime start;
        private LocalDateTime end;
        private String workShopOffer;

        public Builder() {
        }

        public Builder workShopId(UUID val) {
            workShopId = val;
            return this;
        }

        public Builder start(LocalDateTime val) {
            start = val;
            return this;
        }

        public Builder end(LocalDateTime val) {
            end = val;
            return this;
        }

        public Builder workShopOffer(String val) {
            workShopOffer = val;
            return this;
        }

        public AppointmentResponseDTO build() {
            return new AppointmentResponseDTO(this);
        }
    }

    public UUID getWorkShopId() {
        return workShopId;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public String getWorkShopOffer() {
        return workShopOffer;
    }
}
