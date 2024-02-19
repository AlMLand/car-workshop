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
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.proxy.HibernateProxy;

import java.util.Objects;
import java.util.Set;
import java.util.UUID;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.EnumType.STRING;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
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

    public void addAppointmentEntity(AppointmentEntity appointmentEntity) {
        this.appointmentEntities.add(appointmentEntity);
        appointmentEntity.workShopOfferEntity = this;
    }

    public void addAllAppointmentEntities(Set<AppointmentEntity> appointmentEntities) {
        this.appointmentEntities.addAll(appointmentEntities);
        appointmentEntities.forEach(appointmentEntity -> appointmentEntity.workShopOfferEntity = this);
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
        WorkShopOfferEntity that = (WorkShopOfferEntity) o;
        return getWorkShopOfferId() != null && Objects.equals(getWorkShopOfferId(), that.getWorkShopOfferId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ?
                ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() :
                getClass().hashCode();
    }
}
