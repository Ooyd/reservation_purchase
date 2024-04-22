package com.example.ReservationPurchase.product.infrastructure.repository;

import com.example.ReservationPurchase.product.infrastructure.repository.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductJpaRepository extends JpaRepository<ProductEntity, Long> {
}

