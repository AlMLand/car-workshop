package com.almland.carworkshop.infrastructure.adaptor.outbound.persistence.repository;

import com.almland.carworkshop.domain.Offer;
import com.almland.carworkshop.infrastructure.adaptor.outbound.persistence.entity.WorkShopOfferEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface WorkShopOfferRepository extends JpaRepository<WorkShopOfferEntity, UUID> {
    
    WorkShopOfferEntity findByOfferAndWorkShopEntity_WorkShopId(Offer offer, UUID workShopId);

}
