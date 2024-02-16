package com.almland.carworkshop.infrastructure.adaptor.outbound.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "time_slot")
public class TimeSlotEntity {
    @Id
    @Column(updatable = false, nullable = false)
    UUID timeSlotId;
    @Column(updatable = false, nullable = false)
    LocalDateTime startTime;
    @Column(updatable = false, nullable = false)
    LocalDateTime endTime;
    @Column(name = "fk_appointment_id")
    UUID appointmentId;

    public UUID getTimeSlotId() {
        return timeSlotId;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }
}
