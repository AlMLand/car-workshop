package com.almland.carworkshop.domain;

import java.util.Set;
import java.util.UUID;

public class AppointmentSuggestion {
    private UUID appointmentSuggestionId;
    private WorkShop workShop;
    private WorkShopOffer workShopOffer;
    private Set<TimeSlot> possibleTimeSlots;

    public AppointmentSuggestion(UUID appointmentSuggestionId, WorkShop workShop, WorkShopOffer workShopOffer, Set<TimeSlot> possibleTimeSlots) {
        this.appointmentSuggestionId = appointmentSuggestionId;
        this.workShop = workShop;
        this.workShopOffer = workShopOffer;
        this.possibleTimeSlots = possibleTimeSlots;
    }

    public UUID getAppointmentSuggestionId() {
        return appointmentSuggestionId;
    }

    public WorkShop getWorkShop() {
        return workShop;
    }

    public WorkShopOffer getWorkShopOffer() {
        return workShopOffer;
    }

    public Set<TimeSlot> getPossibleTimeSlots() {
        return possibleTimeSlots;
    }
}
