package com.almland.carworkshop.domain;

public class WorkShopOffer {

    private Offer offer;
    private int durationInMin;

    private WorkShopOffer(Builder builder) {
        offer = builder.offer;
        durationInMin = builder.durationInMin;
    }

    public Offer getOffer() {
        return offer;
    }

    public int getDurationInMin() {
        return durationInMin;
    }

    public static final class Builder {
        private Offer offer;
        private int durationInMin;

        public Builder() {
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
