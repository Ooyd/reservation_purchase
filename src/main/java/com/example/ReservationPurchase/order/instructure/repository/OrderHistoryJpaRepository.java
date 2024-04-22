package com.example.ReservationPurchase.order.instructure.repository;


import com.example.ReservationPurchase.order.instructure.repository.entity.OrderHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderHistoryJpaRepository extends JpaRepository<OrderHistoryEntity, Long> {
}
