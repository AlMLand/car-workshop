package com.almland.carworkshop.infrastructure.adaptor.inbound.rest.dto.response;

import java.util.List;
import java.util.UUID;

public class AppointmentSuggestionResponseDTO {

    private UUID workShopId;
    private UUID workShopOfferId;
    private List<TimeSlotResponseDTO> possibleTimeSlots;

    private AppointmentSuggestionResponseDTO(Builder builder) {
        workShopId = builder.workShopId;
        workShopOfferId = builder.workShopOfferId;
        possibleTimeSlots = builder.possibleTimeSlots;
    }

    public UUID getWorkShopId() {
        return workShopId;
    }

    public UUID getWorkShopOfferId() {
        return workShopOfferId;
    }

    public List<TimeSlotResponseDTO> getPossibleTimeSlots() {
        return possibleTimeSlots;
    }

    public static final class Builder {
        private UUID workShopId;
        private UUID workShopOfferId;
        private List<TimeSlotResponseDTO> possibleTimeSlots;

        public Builder() {
        }

        public Builder workShopId(UUID val) {
            workShopId = val;
            return this;
        }

        public Builder workShopOfferId(UUID val) {
            workShopOfferId = val;
            return this;
        }

        public Builder possibleTimeSlots(List<TimeSlotResponseDTO> val) {
            possibleTimeSlots = val;
            return this;
        }

        public AppointmentSuggestionResponseDTO build() {
            return new AppointmentSuggestionResponseDTO(this);
        }
    }
}
