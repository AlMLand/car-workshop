package com.almland.carworkshop.infrastructure.adaptor.outbound.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;

import java.util.Set;
import java.util.UUID;

import static jakarta.persistence.CascadeType.ALL;

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
    @OrderBy("offer ASC")
    @OneToMany(cascade = ALL, mappedBy = "workShopEntity")
    Set<WorkShopOfferEntity> workShopOfferEntities;
    @OneToMany(cascade = ALL, mappedBy = "workShopEntity")
    Set<AppointmentEntity> appointmentEntities;

    void addWorkShopOfferEntity(WorkShopOfferEntity workShopOfferEntity) {
        this.workShopOfferEntities.add(workShopOfferEntity);
        workShopOfferEntity.workShopEntity = this;
    }

    void addAllWorkShopOfferEntities(Set<WorkShopOfferEntity> workShopOfferEntities) {
        this.workShopOfferEntities.addAll(workShopOfferEntities);
        workShopOfferEntities.forEach(workShopOfferEntity -> workShopOfferEntity.workShopEntity = this);
    }

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

    public Set<WorkShopOfferEntity> getWorkShopOfferEntities() {
        return workShopOfferEntities;
    }

    public Set<AppointmentEntity> getAppointmentEntities() {
        return appointmentEntities;
    }
}
