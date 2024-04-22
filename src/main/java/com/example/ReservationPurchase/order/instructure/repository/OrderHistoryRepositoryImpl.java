package com.example.ReservationPurchase.order.instructure.repository;


import com.example.ReservationPurchase.order.application.port.OrderHistoryRepository;
import com.example.ReservationPurchase.order.domain.OrderHistory;
import com.example.ReservationPurchase.order.instructure.repository.entity.OrderHistoryEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@AllArgsConstructor
@Repository
public class OrderHistoryRepositoryImpl implements OrderHistoryRepository {

    private final OrderHistoryJpaRepository orderHistoryJpaRepository;

    @Override
    public OrderHistory save(final OrderHistory orderHistory) {
        return orderHistoryJpaRepository.save(OrderHistoryEntity.from(orderHistory)).toModel();
    }

    @Override
    public Optional<OrderHistory> findByOrderId(final Long orderId) {
        return orderHistoryJpaRepository.findById(orderId).map(OrderHistoryEntity::toModel);
    }

}
