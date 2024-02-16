package com.almland.carworkshop.domain;

import java.util.UUID;

public class Appointment {
    private UUID appointmentId;
    private WorkShop workShop;
    private TimeSlot timeSlot;
    private WorkShopOffer workShopOffer;

    public Appointment(UUID appointmentId, WorkShop workShop, TimeSlot timeSlot, WorkShopOffer workShopOffer) {
        this.appointmentId = appointmentId;
        this.workShop = workShop;
        this.timeSlot = timeSlot;
        this.workShopOffer = workShopOffer;
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
}
