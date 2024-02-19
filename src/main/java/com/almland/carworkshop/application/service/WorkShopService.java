package com.almland.carworkshop.application.service;

import com.almland.carworkshop.application.port.inbound.RestPort;
import com.almland.carworkshop.application.port.outbound.PersistencePort;
import com.almland.carworkshop.domain.Appointment;
import com.almland.carworkshop.domain.Offer;
import com.almland.carworkshop.domain.WorkShopOffer;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Set;
import java.util.UUID;

import static java.time.LocalTime.MAX;
import static java.time.LocalTime.MIDNIGHT;

@Transactional
public class WorkShopService implements RestPort {

    private PersistencePort persistencePort;
    private AppointmentSuggestionService appointmentSuggestionService;

    public WorkShopService(PersistencePort persistencePort, AppointmentSuggestionService appointmentSuggestionService) {
        this.persistencePort = persistencePort;
        this.appointmentSuggestionService = appointmentSuggestionService;
    }

    @Override
    public UUID createAppointment(UUID workShopId, Appointment appointment) {
        var workShop = persistencePort.getWorkShopById(workShopId);
        var availableAppointmentsOnWishDay = persistencePort.getAllAppointments(
                workShopId,
                getEdgeTimeOfWishDay(appointment, MIDNIGHT),
                getEdgeTimeOfWishDay(appointment, MAX),
                null
        );
        var workShopOffer = persistencePort.getWorkShopOfferByOfferAndWorkShopId(
                workShopId,
                appointment.getWorkShopOffer().getOffer()
        );
        setTimeSlotEnd(appointment, workShopOffer);

        var allAppointmentSuggestions = appointmentSuggestionService.getAllAppointmentSuggestions(
                workShopOffer,
                availableAppointmentsOnWishDay,
                workShopId,
                workShopOffer.getWorkShopOfferId(),
                workShop.getMaxParallelAppointments()
        );

        return allAppointmentSuggestions.stream().anyMatch(suggestion -> isTimeStartEqual(appointment, suggestion)) ?
                persistencePort.createAppointment(workShopId, appointment, workShopOffer) :
                null;
    }

    private LocalDateTime getEdgeTimeOfWishDay(Appointment appointment, LocalTime edgeTime) {
        return LocalDateTime.of(appointment.getTimeSlot().getStartTime().toLocalDate(), edgeTime);
    }

    private boolean isTimeStartEqual(Appointment appointment, Appointment suggestion) {
        return suggestion.getTimeSlot().getStartTime().equals(appointment.getTimeSlot().getStartTime());
    }

    private void setTimeSlotEnd(Appointment appointment, WorkShopOffer workShopOffer) {
        appointment
                .getTimeSlot()
                .setEndTime(appointment.getTimeSlot().getStartTime().plusMinutes(workShopOffer.getDurationInMin()));
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
        var workShop = persistencePort.getWorkShopById(workShopId);
        var workShopOffer = persistencePort.getWorkShopOffer(workShopOfferId);
        var availableAppointments = persistencePort.getAllAppointments(workShopId, from, until, null);
        return appointmentSuggestionService.getAllAppointmentSuggestions(
                workShopOffer,
                availableAppointments,
                workShopId,
                workShopOfferId,
                workShop.getMaxParallelAppointments()
        );
    }
}
