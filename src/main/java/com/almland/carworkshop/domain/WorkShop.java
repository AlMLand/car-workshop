package com.almland.carworkshop.domain;

import java.util.Objects;
import java.util.Set;
import java.util.UUID;

public class WorkShop {

    private UUID workShopId;
    private String name;
    private int maxParallelAppointments;
    private Set<WorkShopOffer> workShopOffers;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WorkShop workShop = (WorkShop) o;
        return maxParallelAppointments == workShop.maxParallelAppointments &&
                Objects.equals(workShopId, workShop.workShopId) &&
                Objects.equals(name, workShop.name) &&
                Objects.equals(workShopOffers, workShop.workShopOffers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(workShopId, name, maxParallelAppointments, workShopOffers);
    }

    private WorkShop(Builder builder) {
        workShopId = builder.workShopId;
        name = builder.name;
        maxParallelAppointments = builder.maxParallelAppointments;
        workShopOffers = builder.workShopOffers;
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

    public static final class Builder {
        private UUID workShopId;
        private String name;
        private int maxParallelAppointments;
        private Set<WorkShopOffer> workShopOffers;

        public Builder() {
        }

        public Builder workShopId(UUID val) {
            workShopId = val;
            return this;
        }

        public Builder name(String val) {
            name = val;
            return this;
        }

        public Builder maxParallelAppointments(int val) {
            maxParallelAppointments = val;
            return this;
        }

        public Builder workShopOffers(Set<WorkShopOffer> val) {
            workShopOffers = val;
            return this;
        }

        public WorkShop build() {
            return new WorkShop(this);
        }
    }
}
