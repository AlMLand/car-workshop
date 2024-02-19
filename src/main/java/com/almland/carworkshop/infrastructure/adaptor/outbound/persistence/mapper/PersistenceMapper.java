package com.almland.carworkshop.infrastructure.adaptor.outbound.persistence.mapper;

import com.almland.carworkshop.domain.Appointment;
import com.almland.carworkshop.domain.TimeSlot;
import com.almland.carworkshop.domain.WorkShop;
import com.almland.carworkshop.domain.WorkShopOffer;
import com.almland.carworkshop.infrastructure.adaptor.outbound.persistence.entity.AppointmentEntity;
import com.almland.carworkshop.infrastructure.adaptor.outbound.persistence.entity.TimeSlotEntity;
import com.almland.carworkshop.infrastructure.adaptor.outbound.persistence.entity.WorkShopEntity;
import com.almland.carworkshop.infrastructure.adaptor.outbound.persistence.entity.WorkShopOfferEntity;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class PersistenceMapper {

    public Set<Appointment> mapToAppointment(Collection<AppointmentEntity> appointments) {
        return appointments.stream()
                .filter(Objects::nonNull)
                .map(appointmentEntity -> Appointment.builder()
                        .appointmentId(appointmentEntity.getAppointmentId())
                        .workShop(mapToWorkShop(appointmentEntity.getWorkShopEntity()))
                        .timeSlot(mapToTimeSlot(appointmentEntity.getTimeSlotEntity()))
                        .workShopOffer(mapToWorkShopOffer(appointmentEntity.getWorkShopOfferEntity()))
                        .build()
                )
                .collect(Collectors.toSet());
    }

    private TimeSlot mapToTimeSlot(TimeSlotEntity timeSlotEntity) {
        return timeSlotEntity == null ?
                null :
                TimeSlot.builder()
                        .timeSlotId(timeSlotEntity.getTimeSlotId())
                        .startTime(timeSlotEntity.getStartTime())
                        .endTime(timeSlotEntity.getEndTime())
                        .build();
    }

    public WorkShop mapToWorkShop(WorkShopEntity workShopEntity) {
        return workShopEntity == null ?
                null :
                WorkShop.builder()
                        .workShopId(workShopEntity.getWorkShopId())
                        .name(workShopEntity.getName())
                        .maxParallelAppointments(workShopEntity.getMaxParallelAppointments())
                        .workShopOffers(mapToWorkShopOffers(workShopEntity.getWorkShopOfferEntities()))
                        .build();
    }

    private Set<WorkShopOffer> mapToWorkShopOffers(Collection<WorkShopOfferEntity> workShopOfferEntities) {
        return workShopOfferEntities
                .stream()
                .map(this::mapToWorkShopOffer)
                .collect(Collectors.toSet());
    }

    public WorkShopOffer mapToWorkShopOffer(WorkShopOfferEntity workShopOfferEntity) {
        return WorkShopOffer.builder()
                .workShopOfferId(workShopOfferEntity.getWorkShopOfferId())
                .offer(workShopOfferEntity.getOffer())
                .durationInMin(workShopOfferEntity.getDurationInMin())
                .build();
    }

    public AppointmentEntity mapToAppointmentEntity(
            WorkShopEntity workShopEntity,
            Appointment appointment,
            WorkShopOffer workShopOffer
    ) {
        var appointmentId = UUID.randomUUID();
        return AppointmentEntity.builder()
                .appointmentId(appointmentId)
                .workShopOfferEntity(mapToWorkShopOfferEntity(workShopEntity, workShopOffer))
                .timeSlotEntity(TimeSlotEntity.builder()
                        .timeSlotId(UUID.randomUUID())
                        .startTime(appointment.getTimeSlot().getStartTime())
                        .endTime(appointment.getTimeSlot().getEndTime())
                        .appointmentId(appointmentId)
                        .build())
                .workShopEntity(workShopEntity)
                .build();
    }

    private WorkShopOfferEntity mapToWorkShopOfferEntity(WorkShopEntity workShopEntity, WorkShopOffer workShopOffer) {
        return WorkShopOfferEntity.builder()
                .workShopOfferId(workShopOffer.getWorkShopOfferId())
                .offer(workShopOffer.getOffer())
                .durationInMin(workShopOffer.getDurationInMin())
                .workShopEntity(workShopEntity)
                .build();
    }
}
