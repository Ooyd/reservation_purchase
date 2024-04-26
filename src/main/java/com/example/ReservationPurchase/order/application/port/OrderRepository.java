package com.example.ReservationPurchase.order.application.port;

import com.example.ReservationPurchase.order.domain.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.Optional;

public interface OrderRepository {
    Order save(Order order);

    Optional<Order> findById(Long orderId);

    Page<Order> findAll(Pageable pageable);
}
