package com.almland.carworkshop.application.port.inbound;

import com.almland.carworkshop.domain.Appointment;
import com.almland.carworkshop.domain.AppointmentSuggestion;
import com.almland.carworkshop.domain.Offer;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

public interface RestPort {
    boolean createAppointment(UUID workShopId, Appointment appointment);

    Appointment getAppointment(UUID workShopId, UUID appointmentId);

    Set<Appointment> getAllAppointments(UUID workShopId, LocalDateTime from, LocalDateTime until, Offer offer);

    Set<AppointmentSuggestion> getAppointmentSuggestions(UUID workShopId, Offer offer, LocalDateTime from, LocalDateTime until);
}
