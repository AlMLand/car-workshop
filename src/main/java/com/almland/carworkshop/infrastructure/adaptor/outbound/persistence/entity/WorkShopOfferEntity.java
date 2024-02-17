package com.almland.carworkshop.infrastructure.adaptor.outbound.persistence.entity;

import com.almland.carworkshop.domain.Offer;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.Set;
import java.util.UUID;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.EnumType.STRING;

@Entity
@Table(name = "work_shop_offer")
public class WorkShopOfferEntity {
    @Id
    @Column(updatable = false, nullable = false)
    UUID workShopOfferId;
    @Column(nullable = false)
    @Enumerated(STRING)
    Offer offer;
    @Column(nullable = false)
    int durationInMin;
    @ManyToOne
    @JoinColumn(name = "fk_work_shop_id")
    WorkShopEntity workShopEntity;
    @OneToMany(cascade = ALL, mappedBy = "workShopOfferEntity")
    Set<AppointmentEntity> appointmentEntities;

    void addAppointmentEntity(AppointmentEntity appointmentEntity) {
        this.appointmentEntities.add(appointmentEntity);
        appointmentEntity.workShopOfferEntity = this;
    }

    void addAllAppointmentEntities(Set<AppointmentEntity> appointmentEntities) {
        this.appointmentEntities.addAll(appointmentEntities);
        appointmentEntities.forEach(appointmentEntity -> appointmentEntity.workShopOfferEntity = this);
    }

    public UUID getWorkShopOfferId() {
        return workShopOfferId;
    }

    public Offer getOffer() {
        return offer;
    }

    public int getDurationInMin() {
        return durationInMin;
    }

    public WorkShopEntity getWorkShopEntity() {
        return workShopEntity;
    }

    public Set<AppointmentEntity> getAppointmentEntities() {
        return appointmentEntities;
    }
}
