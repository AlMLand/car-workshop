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
import java.util.stream.Collectors;

@Component
public class PersistenceMapper {
    public Set<Appointment> mapToAppointment(Collection<AppointmentEntity> appointments) {
        return appointments.stream()
                .filter(Objects::nonNull)
                .map(appointmentEntity -> new Appointment(
                                appointmentEntity.getAppointmentId(),
                                mapToWorkShop(appointmentEntity.getWorkShopEntity()),
                                mapToTimeSlot(appointmentEntity.getTimeSlotEntity()),
                                mapToWorkShopOffer(appointmentEntity.getWorkShopOfferEntity())
                        )
                )
                .collect(Collectors.toSet());
    }

    private TimeSlot mapToTimeSlot(TimeSlotEntity timeSlotEntity) {
        return timeSlotEntity == null ?
                null :
                new TimeSlot(
                        timeSlotEntity.getTimeSlotId(),
                        timeSlotEntity.getStartTime(),
                        timeSlotEntity.getEndTime()
                );
    }

    private WorkShop mapToWorkShop(WorkShopEntity workShopEntity) {
        return workShopEntity == null ?
                null :
                new WorkShop(
                        workShopEntity.getWorkShopId(),
                        workShopEntity.getName(),
                        workShopEntity.getMaxParallelAppointments(),
                        mapToWorkShopOffers(workShopEntity.getWorkShopOfferEntities())
                );
    }

    private Set<WorkShopOffer> mapToWorkShopOffers(Collection<WorkShopOfferEntity> workShopOfferEntities) {
        return workShopOfferEntities
                .stream()
                .map(this::mapToWorkShopOffer)
                .collect(Collectors.toSet());
    }

    private WorkShopOffer mapToWorkShopOffer(WorkShopOfferEntity workShopOfferEntity) {
        return new WorkShopOffer(workShopOfferEntity.getOffer(), workShopOfferEntity.getDurationInMin());
    }
}
