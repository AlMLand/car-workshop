package com.almland.carworkshop.domain;

import java.util.Set;
import java.util.UUID;

public class WorkShop {
    private UUID workShopId;
    private String name;
    private int maxParallelAppointments;
    private Set<WorkShopOffer> workShopOffers;

    public WorkShop(UUID workShopId, String name, int maxParallelAppointments, Set<WorkShopOffer> workShopOffers) {
        this.workShopId = workShopId;
        this.name = name;
        this.maxParallelAppointments = maxParallelAppointments;
        this.workShopOffers = workShopOffers;
    }

    public UUID getWorkShopId() {
        return workShopId;
    }

    public String getName() {
        return name;
    }

    public int getMaxParallelAppointments() {
        return maxParallelAppointments;
    }

    public Set<WorkShopOffer> getWorkShopOffers() {
        return workShopOffers;
    }
}
