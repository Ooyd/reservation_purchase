package com.example.ReservationPurchase.cart.presentation.response;

import com.example.ReservationPurchase.cart.application.port.CartRepository;
import com.example.ReservationPurchase.cart.domain.Cart;
import com.example.ReservationPurchase.product.presentation.response.ProductResponse;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class CartResponse {
    private Long cartId;
    private ProductResponse product;
    private LocalDateTime modifiedAt;

    @Builder
    public CartResponse(Long cartId, ProductResponse product, LocalDateTime modifiedAt) {
        this.cartId = cartId;
        this.product = product;
        this.modifiedAt = modifiedAt;
    }
    public static CartResponse from(Cart cart){
        return CartResponse.builder()
                .cartId(cart.getId())
                .product(ProductResponse.from(cart.getProduct()))
                .build();
    }
}