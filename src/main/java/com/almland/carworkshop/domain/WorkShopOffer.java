package com.almland.carworkshop.domain;

public class WorkShopOffer {
    private Offer offer;
    private int durationInMin;

    public WorkShopOffer(Offer offer, int durationInMin) {
        this.offer = offer;
        this.durationInMin = durationInMin;
    }

    public Offer getOffer() {
        return offer;
    }

    public int getDurationInMin() {
        return durationInMin;
    }
}
