package com.almland.carworkshop.infrastructure.adaptor.inbound.rest.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.UUID;

@Getter
@Builder
public class AppointmentSuggestionResponseDTO {
    private UUID workShopId;
    private UUID workShopOfferId;
    private List<TimeSlotResponseDTO> possibleTimeSlots;
}
