package com.almland.carworkshop.infrastructure.adaptor.inbound.rest.dto.response;

import com.almland.carworkshop.domain.Offer;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Getter
public class AppointmentSuggestionDTO {
    private UUID workShopId;
    private Offer offer;
    private LocalDateTime start;
    private LocalDateTime end;
    private Set<LocalDateTime> possibleAppointmentStarts;
}
