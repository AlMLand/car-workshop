package com.almland.carworkshop.infrastructure.adaptor.outbound.persistence.entity;

import com.almland.carworkshop.domain.WorkShopOffer;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import java.util.UUID;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.EnumType.STRING;

@Entity
@Table(name = "appointment")
public class AppointmentEntity {
    @Id
    @Column(updatable = false, nullable = false)
    UUID appointmentId;
    @Enumerated(STRING)
    WorkShopOffer workShopOffer;
    @OneToOne(cascade = ALL)
    @JoinColumn(name = "fk_time_slot_id", nullable = false)
    TimeSlotEntity timeSlotEntity;
    @ManyToOne
    @JoinColumn(name = "fk_work_shop_id")
    WorkShopEntity workShopEntity;

    public UUID getAppointmentId() {
        return appointmentId;
    }

    public WorkShopOffer getWorkShopOffer() {
        return workShopOffer;
    }

    public TimeSlotEntity getTimeSlotEntity() {
        return timeSlotEntity;
    }

    public WorkShopEntity getWorkShopEntity() {
        return workShopEntity;
    }
}
