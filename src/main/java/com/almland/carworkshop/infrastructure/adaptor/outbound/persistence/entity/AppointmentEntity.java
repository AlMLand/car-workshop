package com.almland.carworkshop.infrastructure.adaptor.outbound.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.proxy.HibernateProxy;

import java.util.Objects;
import java.util.UUID;

import static jakarta.persistence.CascadeType.ALL;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "appointment")
public class AppointmentEntity {
    @Id
    @Column(updatable = false, nullable = false)
    UUID appointmentId;
    @ManyToOne
    @JoinColumn(name = "fk_work_shop_offer_id")
    WorkShopOfferEntity workShopOfferEntity;
    @OneToOne(cascade = ALL)
    @JoinColumn(name = "fk_time_slot_id", nullable = false)
    TimeSlotEntity timeSlotEntity;
    @ManyToOne
    @JoinColumn(name = "fk_work_shop_id")
    WorkShopEntity workShopEntity;

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
        AppointmentEntity that = (AppointmentEntity) o;
        return getAppointmentId() != null && Objects.equals(getAppointmentId(), that.getAppointmentId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ?
                ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() :
                getClass().hashCode();
    }
}
