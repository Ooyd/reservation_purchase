package com.example.ReservationPurchase.cart.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CartUpdate {

    private String productId;

    private int quantity;

    @Builder
    public CartUpdate(String productId, int quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }
}
