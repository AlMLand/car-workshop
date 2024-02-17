package com.almland.carworkshop.infrastructure.adaptor.inbound.rest.dto.response;

import com.almland.carworkshop.domain.Offer;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

public record AppointmentSuggestionDTO(
        UUID workShopId,
        Offer offer,
        LocalDateTime start,
        LocalDateTime end,
        Set<LocalDateTime> possibleAppointmentStarts
) {
    @Override
    public UUID workShopId() {
        return workShopId;
    }

    public Offer offer() {
        return offer;
    }

    @Override
    public LocalDateTime start() {
        return start;
    }

    @Override
    public LocalDateTime end() {
        return end;
    }

    @Override
    public Set<LocalDateTime> possibleAppointmentStarts() {
        return possibleAppointmentStarts;
    }
}
