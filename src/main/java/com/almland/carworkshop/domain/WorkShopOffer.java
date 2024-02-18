package com.almland.carworkshop.domain;

import java.util.Objects;
import java.util.UUID;

public class WorkShopOffer {

    private UUID workShopOfferId;
    private Offer offer;
    private int durationInMin;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WorkShopOffer that = (WorkShopOffer) o;
        return durationInMin == that.durationInMin &&
                Objects.equals(workShopOfferId, that.workShopOfferId) &&
                offer == that.offer;
    }

    @Override
    public int hashCode() {
        return Objects.hash(workShopOfferId, offer, durationInMin);
    }

    public UUID getWorkShopOfferId() {
        return workShopOfferId;
    }

    public Offer getOffer() {
        return offer;
    }

    public int getDurationInMin() {
        return durationInMin;
    }

    private WorkShopOffer(Builder builder) {
        workShopOfferId = builder.workShopOfferId;
        offer = builder.offer;
        durationInMin = builder.durationInMin;
    }

    public static final class Builder {
        private UUID workShopOfferId;
        private Offer offer;
        private int durationInMin;

        public Builder() {
        }

        public Builder workShopOfferId(UUID val) {
            workShopOfferId = val;
            return this;
        }

        public Builder offer(Offer val) {
            offer = val;
            return this;
        }

        public Builder durationInMin(int val) {
            durationInMin = val;
            return this;
        }

        public WorkShopOffer build() {
            return new WorkShopOffer(this);
        }
    }
}
