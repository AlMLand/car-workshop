package com.almland.carworkshop.application.service;

import com.almland.carworkshop.application.port.inbound.RestPort;
import com.almland.carworkshop.application.port.outbound.PersistencePort;
import com.almland.carworkshop.domain.Appointment;
import com.almland.carworkshop.domain.Offer;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Transactional
public class WorkShopService implements RestPort {

    private PersistencePort persistencePort;

    public WorkShopService(PersistencePort persistencePort) {
        this.persistencePort = persistencePort;
    }

    @Override
    public UUID createAppointment(UUID workShopId, Appointment appointment) {
        return null;
    }

    @Override
    public Appointment getAppointment(UUID workShopId, UUID appointmentId) {
        return persistencePort.getAppointment(workShopId, appointmentId);
    }

    @Override
    public Set<Appointment> getAllAppointments(
            UUID workShopId,
            LocalDateTime from,
            LocalDateTime until,
            Offer offer
    ) {
        return persistencePort.getAllAppointments(workShopId, from, until, offer);
    }

    @Override
    public Set<Appointment> getAppointmentSuggestions(
            UUID workShopId,
            UUID workShopOfferId,
            LocalDateTime from,
            LocalDateTime until
    ) {
        var workShopOffer = persistencePort.getWorkShopOffer(workShopOfferId);
        var availableAppointments = persistencePort.getAllAppointments(workShopId, from, until, null);
        return null;
    }
}
