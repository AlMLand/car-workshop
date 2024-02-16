package com.almland.carworkshop.application.service;

import com.almland.carworkshop.application.port.inbound.RestPort;
import com.almland.carworkshop.application.port.outbound.PersistencePort;
import com.almland.carworkshop.domain.Appointment;
import com.almland.carworkshop.domain.AppointmentSuggestion;
import com.almland.carworkshop.domain.WorkShopOffer;
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
    public String createAppointment(UUID workShopId, Appointment appointment) {
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
            WorkShopOffer workShopOffer
    ) {
        return persistencePort.getAllAppointments(workShopId, from, until, workShopOffer);
    }

    @Override
    public Set<AppointmentSuggestion> getAppointmentSuggestions(UUID workShopId, WorkShopOffer workShopOffer, LocalDateTime from, LocalDateTime until) {
        return null;
    }
}
