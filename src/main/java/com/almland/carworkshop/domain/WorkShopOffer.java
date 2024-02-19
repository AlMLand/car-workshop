package com.almland.carworkshop.domain;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
@EqualsAndHashCode
public class WorkShopOffer {

    private UUID workShopOfferId;
    private Offer offer;
    private int durationInMin;
}
