package com.almland.carworkshop.infrastructure.adaptor.outbound.persistence.repository;

import com.almland.carworkshop.infrastructure.adaptor.outbound.persistence.entity.WorkShopOfferEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface WorkShopOfferRepository extends JpaRepository<WorkShopOfferEntity, UUID> {
}
