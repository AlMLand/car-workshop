package com.almland.carworkshop.infrastructure.adaptor.outbound.persistence;

import com.almland.carworkshop.application.port.outbound.PersistencePort;
import com.almland.carworkshop.domain.Appointment;
import com.almland.carworkshop.domain.Offer;
import com.almland.carworkshop.domain.WorkShop;
import com.almland.carworkshop.domain.WorkShopOffer;
import com.almland.carworkshop.infrastructure.adaptor.outbound.persistence.mapper.PersistenceMapper;
import com.almland.carworkshop.infrastructure.adaptor.outbound.persistence.repository.AppointmentRepository;
import com.almland.carworkshop.infrastructure.adaptor.outbound.persistence.repository.WorkShopOfferRepository;
import com.almland.carworkshop.infrastructure.adaptor.outbound.persistence.repository.WorkShopRepository;
import com.almland.carworkshop.infrastructure.adaptor.outbound.persistence.specification.AppointmentSpecification;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Service
public class PersistenceAdaptor implements PersistencePort {

    private PersistenceMapper persistenceMapper;
    private WorkShopRepository workShopRepository;
    private AppointmentRepository appointmentRepository;
    private WorkShopOfferRepository workShopOfferRepository;
    private AppointmentSpecification appointmentSpecification;
    @PersistenceContext
    private EntityManager entityManager;

    public PersistenceAdaptor(
            PersistenceMapper persistenceMapper,
            WorkShopRepository workShopRepository,
            AppointmentRepository appointmentRepository,
            WorkShopOfferRepository workShopOfferRepository,
            AppointmentSpecification appointmentSpecification
    ) {
        this.persistenceMapper = persistenceMapper;
        this.workShopRepository = workShopRepository;
        this.appointmentRepository = appointmentRepository;
        this.workShopOfferRepository = workShopOfferRepository;
        this.appointmentSpecification = appointmentSpecification;
    }

    @Transactional
    @Override
    public UUID createAppointment(UUID workShopId, Appointment appointment, WorkShopOffer workShopOffer) {
        var workShopEntity = workShopRepository.getReferenceById(workShopId);
        var appointmentEntity = persistenceMapper.mapToAppointmentEntity(workShopEntity, appointment, workShopOffer);
        workShopEntity.addAppointmentEntity(appointmentEntity);
        entityManager.flush();
        return appointmentEntity.getAppointmentId();
    }

    @Transactional(readOnly = true)
    @Override
    public Appointment getAppointment(UUID workShopId, UUID appointmentId) {
        var appointment = appointmentRepository
                .findByWorkShopEntity_WorkShopIdAndAppointmentId(workShopId, appointmentId);
        return persistenceMapper
                .mapToAppointment(Set.of(appointment))
                .stream()
                .findFirst()
                .orElse(null);
    }

    @Transactional(readOnly = true)
    @Override
    public Set<Appointment> getAllAppointments(
            UUID workShopId,
            LocalDateTime from,
            LocalDateTime until,
            Offer offer
    ) {
        var allAppointments = appointmentRepository
                .findAll(appointmentSpecification.allAppointmentsBySpecification(workShopId, from, until, offer));
        return persistenceMapper.mapToAppointment(allAppointments);
    }

    @Override
    public WorkShopOffer getWorkShopOffer(UUID workShopOfferId) {
        return persistenceMapper.mapToWorkShopOffer(workShopOfferRepository.getReferenceById(workShopOfferId));
    }

    @Override
    public WorkShopOffer getWorkShopOfferByOfferAndWorkShopId(UUID workShopId, Offer offer) {
        return persistenceMapper.mapToWorkShopOffer(
                workShopOfferRepository.findByOfferAndWorkShopEntity_WorkShopId(offer, workShopId)
        );
    }

    @Override
    public WorkShop getWorkShopById(UUID workShopId) {
        return persistenceMapper.mapToWorkShop(workShopRepository.getReferenceById(workShopId));
    }
}
