package com.example.ReservationPurchase.cart.infrastructure;

import com.example.ReservationPurchase.cart.domain.Cart;
import com.example.ReservationPurchase.cart.infrastructure.entity.CartEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartJpaRepository extends JpaRepository<CartEntity, Long> {

    List<CartEntity> findByUserId(Long userId);

}
