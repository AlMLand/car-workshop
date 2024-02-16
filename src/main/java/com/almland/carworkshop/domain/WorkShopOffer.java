package com.almland.carworkshop.domain;

public enum WorkShopOffer {
    MOT(240), OIL(15), WHE(30), FIX(180), INS(60);

    private int durationInMin;

    WorkShopOffer(int durationInMin) {
        this.durationInMin = durationInMin;
    }

    public int getDurationInMin() {
        return durationInMin;
    }
}
