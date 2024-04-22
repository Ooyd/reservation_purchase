package com.example.ReservationPurchase.order.infrastructure;

import com.example.ReservationPurchase.order.infrastructure.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderJpaRepository extends JpaRepository<OrderEntity, Long> {
}
