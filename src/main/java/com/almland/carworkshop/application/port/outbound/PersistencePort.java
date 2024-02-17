package com.almland.carworkshop.application.port.outbound;

import com.almland.carworkshop.domain.Appointment;
import com.almland.carworkshop.domain.Offer;
import com.almland.carworkshop.domain.WorkShopOffer;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

public interface PersistencePort {
    String createAppointment(UUID workShopId, Appointment appointment);

    Appointment getAppointment(UUID workShopId, UUID appointmentId);

    Set<Appointment> getAllAppointments(UUID workShopId, LocalDateTime from, LocalDateTime until, Offer offer);

    WorkShopOffer getWorkShopOffer(UUID workShopOfferId);
}
