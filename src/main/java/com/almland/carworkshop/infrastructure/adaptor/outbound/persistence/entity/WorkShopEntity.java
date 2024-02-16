package com.almland.carworkshop.infrastructure.adaptor.outbound.persistence.entity;

import com.almland.carworkshop.domain.WorkShopOffer;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;

import java.util.Set;
import java.util.UUID;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.EnumType.STRING;

@Entity
@Table(name = "work_shop")
public class WorkShopEntity {
    @Id
    @Column(updatable = false, nullable = false)
    UUID workShopId;
    @Column(nullable = false)
    String name;
    @Column(nullable = false)
    int maxParallelAppointments;
    @ElementCollection
    @CollectionTable(name = "work_shop_offer", joinColumns = @JoinColumn(name = "work_shop_id"))
    @Column(name = "offer")
    @Enumerated(STRING)
    Set<WorkShopOffer> workShopOffers;
    @OrderBy("workShopOffer ASC")
    @OneToMany(cascade = ALL, mappedBy = "workShopEntity")
    Set<AppointmentEntity> appointmentEntities;

    void addAppointmentEntity(AppointmentEntity appointmentEntity) {
        appointmentEntities.add(appointmentEntity);
        appointmentEntity.workShopEntity = this;
    }

    void addAllAppointmentEntities(Set<AppointmentEntity> appointmentEntities) {
        this.appointmentEntities.addAll(appointmentEntities);
        appointmentEntities.forEach(appointmentEntity -> appointmentEntity.workShopEntity = this);
    }

    public UUID getWorkShopId() {
        return workShopId;
    }

    public String getName() {
        return name;
    }

    public int getMaxParallelAppointments() {
        return maxParallelAppointments;
    }

    public Set<WorkShopOffer> getWorkShopOffers() {
        return workShopOffers;
    }

    public Set<AppointmentEntity> getAppointmentEntities() {
        return appointmentEntities;
    }
}
