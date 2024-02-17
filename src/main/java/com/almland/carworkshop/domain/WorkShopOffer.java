package com.almland.carworkshop.domain;

import java.util.UUID;

public class WorkShopOffer {

    private UUID workShopOfferId;
    private Offer offer;
    private int durationInMin;

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
