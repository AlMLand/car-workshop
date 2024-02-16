package com.almland.carworkshop.infrastructure.adaptor.inbound.rest.dto;

import com.almland.carworkshop.domain.WorkShopOffer;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

public record AppointmentSuggestionRestDTO(
        UUID workShopId,
        WorkShopOffer workShopOffer,
        LocalDateTime start,
        LocalDateTime end,
        Set<LocalDateTime> possibleAppointmentStarts
) {
}
