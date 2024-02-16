package com.almland.carworkshop.infrastructure.adaptor.outbound.persistence.repository;

import com.almland.carworkshop.infrastructure.adaptor.outbound.persistence.entity.AppointmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface AppointmentRepository extends JpaRepository<AppointmentEntity, UUID>, JpaSpecificationExecutor<AppointmentEntity> {
    AppointmentEntity findByWorkShopEntity_WorkShopIdAndAppointmentId(UUID workShopId, UUID appointmentId);
}
