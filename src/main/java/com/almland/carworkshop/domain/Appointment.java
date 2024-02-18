package com.almland.carworkshop.domain;

import java.util.Objects;
import java.util.UUID;

public class Appointment {

    private UUID appointmentId;
    private WorkShop workShop;
    private TimeSlot timeSlot;
    private WorkShopOffer workShopOffer;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Appointment that = (Appointment) o;
        return Objects.equals(appointmentId, that.appointmentId) &&
                Objects.equals(workShop, that.workShop) &&
                Objects.equals(timeSlot, that.timeSlot) &&
                Objects.equals(workShopOffer, that.workShopOffer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(appointmentId, workShop, timeSlot, workShopOffer);
    }

    private Appointment(Builder builder) {
        appointmentId = builder.appointmentId;
        workShop = builder.workShop;
        timeSlot = builder.timeSlot;
        workShopOffer = builder.workShopOffer;
    }

    public UUID getAppointmentId() {
        return appointmentId;
    }

    public WorkShop getWorkShop() {
        return workShop;
    }

    public TimeSlot getTimeSlot() {
        return timeSlot;
    }

    public WorkShopOffer getWorkShopOffer() {
        return workShopOffer;
    }

    public static final class Builder {
        private UUID appointmentId;
        private WorkShop workShop;
        private TimeSlot timeSlot;
        private WorkShopOffer workShopOffer;

        public Builder() {
        }

        public Builder appointmentId(UUID val) {
            appointmentId = val;
            return this;
        }

        public Builder workShop(WorkShop val) {
            workShop = val;
            return this;
        }

        public Builder timeSlot(TimeSlot val) {
            timeSlot = val;
            return this;
        }

        public Builder workShopOffer(WorkShopOffer val) {
            workShopOffer = val;
            return this;
        }

        public Appointment build() {
            return new Appointment(this);
        }
    }
}
