package com.example.ReservationPurchase.product.infrastructure.repository;

import com.example.ReservationPurchase.product.infrastructure.repository.entity.ReservationTimeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationTimeJpaRepository extends JpaRepository<ReservationTimeEntity, Long> {
}
