package com.example.ReservationPurchase.order.instructure.repository;


import com.example.ReservationPurchase.order.instructure.repository.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderJpaRepository extends JpaRepository<OrderEntity, Long> {
}
