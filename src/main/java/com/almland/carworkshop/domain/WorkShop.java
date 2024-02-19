package com.almland.carworkshop.domain;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.Set;
import java.util.UUID;

@Getter
@Builder
@EqualsAndHashCode
public class WorkShop {

    private UUID workShopId;
    private String name;
    private int maxParallelAppointments;
    private Set<WorkShopOffer> workShopOffers;
}
