package com.almland.carworkshop.infrastructure.adaptor.outbound.persistence.repository;

import com.almland.carworkshop.infrastructure.adaptor.outbound.persistence.entity.WorkShopEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface WorkShopRepository extends JpaRepository<WorkShopEntity, UUID> {
}
