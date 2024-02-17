package com.almland.carworkshop.domain;

import java.util.Set;
import java.util.UUID;

public class AppointmentSuggestion {

    private UUID appointmentSuggestionId;
    private WorkShop workShop;
    private WorkShopOffer workShopOffer;
    private Set<TimeSlot> possibleTimeSlots;

    private AppointmentSuggestion(Builder builder) {
        appointmentSuggestionId = builder.appointmentSuggestionId;
        workShop = builder.workShop;
        workShopOffer = builder.workShopOffer;
        possibleTimeSlots = builder.possibleTimeSlots;
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

    public static final class Builder {
        private UUID appointmentSuggestionId;
        private WorkShop workShop;
        private WorkShopOffer workShopOffer;
        private Set<TimeSlot> possibleTimeSlots;

        public Builder() {
        }

        public Builder appointmentSuggestionId(UUID val) {
            appointmentSuggestionId = val;
            return this;
        }

        public Builder workShop(WorkShop val) {
            workShop = val;
            return this;
        }

        public Builder workShopOffer(WorkShopOffer val) {
            workShopOffer = val;
            return this;
        }

        public Builder possibleTimeSlots(Set<TimeSlot> val) {
            possibleTimeSlots = val;
            return this;
        }

        public AppointmentSuggestion build() {
            return new AppointmentSuggestion(this);
        }
    }
}
