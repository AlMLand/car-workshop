package com.almland.carworkshop.application.port.inbound;

import com.almland.carworkshop.domain.Appointment;
import com.almland.carworkshop.domain.Offer;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

public interface RestPort {
    UUID createAppointment(UUID workShopId, Appointment appointment);

    Appointment getAppointment(UUID workShopId, UUID appointmentId);

    Set<Appointment> getAllAppointments(UUID workShopId, LocalDateTime from, LocalDateTime until, Offer offer);

    Set<Appointment> getAppointmentSuggestions(UUID workShopId, UUID workShopOfferId, LocalDateTime from, LocalDateTime until);
}
