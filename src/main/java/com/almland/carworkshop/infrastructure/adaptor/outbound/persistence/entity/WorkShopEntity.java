package com.almland.carworkshop.infrastructure.adaptor.outbound.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.proxy.HibernateProxy;

import java.util.Objects;
import java.util.Set;
import java.util.UUID;

import static jakarta.persistence.CascadeType.ALL;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
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

    public void addWorkShopOfferEntity(WorkShopOfferEntity workShopOfferEntity) {
        this.workShopOfferEntities.add(workShopOfferEntity);
        workShopOfferEntity.workShopEntity = this;
    }

    public void addAllWorkShopOfferEntities(Set<WorkShopOfferEntity> workShopOfferEntities) {
        this.workShopOfferEntities.addAll(workShopOfferEntities);
        workShopOfferEntities.forEach(workShopOfferEntity -> workShopOfferEntity.workShopEntity = this);
    }

    public void addAppointmentEntity(AppointmentEntity appointmentEntity) {
        appointmentEntities.add(appointmentEntity);
        appointmentEntity.workShopEntity = this;
    }

    public void addAllAppointmentEntities(Set<AppointmentEntity> appointmentEntities) {
        this.appointmentEntities.addAll(appointmentEntities);
        appointmentEntities.forEach(appointmentEntity -> appointmentEntity.workShopEntity = this);
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ?
                ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() :
                o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ?
                ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() :
                this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        WorkShopEntity that = (WorkShopEntity) o;
        return getWorkShopId() != null && Objects.equals(getWorkShopId(), that.getWorkShopId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ?
                ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() :
                getClass().hashCode();
    }
}
