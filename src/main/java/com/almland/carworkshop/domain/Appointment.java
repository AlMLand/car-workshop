package com.almland.carworkshop.domain;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
@EqualsAndHashCode
public class Appointment {

    private UUID appointmentId;
    private WorkShop workShop;
    private TimeSlot timeSlot;
    private WorkShopOffer workShopOffer;
}
