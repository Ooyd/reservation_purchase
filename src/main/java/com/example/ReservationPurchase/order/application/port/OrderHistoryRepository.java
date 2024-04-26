package com.example.ReservationPurchase.order.application.port;


import com.example.ReservationPurchase.order.domain.OrderHistory;

import java.util.Optional;

public interface OrderHistoryRepository {

    OrderHistory save(OrderHistory orderHistory);

    Optional<OrderHistory> findByOrderId(Long orderId);

}
