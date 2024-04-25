package com.example.ReservationPurchase.cart.infrastructure;

import com.example.ReservationPurchase.cart.application.port.CartRepository;
import com.example.ReservationPurchase.cart.domain.Cart;
import com.example.ReservationPurchase.cart.infrastructure.entity.CartEntity;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class CartJpaRepositoryImpl implements CartRepository {
    private final CartJpaRepository cartJpaRepository;

    public CartJpaRepositoryImpl(CartJpaRepository cartJpaRepository) {
        this.cartJpaRepository = cartJpaRepository;
    }

    @Override
    public Cart save(Cart cart) {
        return cartJpaRepository.save(CartEntity.from(cart))
                .toModel();
    }


    @Override
    public List<Cart> findByUserId(Long userId) {
        return cartJpaRepository.findByUserId(userId)
                .stream()
                .map(CartEntity::toModel)
                .collect(Collectors.toList());
    }

//    @Override
//    public Optional<Cart> findById(Long id) {
//        return cartJpaRepository.findById(id)
//                .map(CartEntity::toModel);
//    }

    @Override
    public void delete(Long id) {
        cartJpaRepository.deleteById(id);
    }


}
