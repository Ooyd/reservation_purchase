package com.example.ReservationPurchase.cart.application.port;

import com.example.ReservationPurchase.cart.domain.Cart;
import com.example.ReservationPurchase.cart.infrastructure.entity.CartEntity;

import java.util.List;
import java.util.Optional;

public interface CartRepository {
    Cart save(Cart cart);

    List<Cart> findByUserId(Long userId);

    void delete(Long id);

    Optional<Cart> findById(Long id);

    void update(Long cartId, int quantity);
}
