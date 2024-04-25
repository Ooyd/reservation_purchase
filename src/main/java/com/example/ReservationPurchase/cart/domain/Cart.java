package com.example.ReservationPurchase.cart.domain;

import com.example.ReservationPurchase.product.domain.Product;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class Cart {
    private Long id;
    private Long memberId;
    private int quantity;
    private Product product;
    protected LocalDateTime createdAt;
    protected LocalDateTime updatedAt;

    @Builder
    public Cart(Long id, Long memberId, Product product, LocalDateTime createdAt, LocalDateTime updatedAt, int quantity) {
        this.id = id;
        this.memberId = memberId;
        this.product = product;
        this.quantity = quantity;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }


    public static Cart create(final CartCreate cartCreate) {
        return Cart.builder()
                .memberId(cartCreate.getMemberId())
                .product(cartCreate.getProduct())
                .quantity(cartCreate.getQuantity())
                .build();
    }

}
