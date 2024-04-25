package com.example.ReservationPurchase.cart.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CartUpdate {

    private String memberId;

    private String productId;

    private int quantity;

    @Builder
    public CartUpdate(String memberId, String productId, int quantity) {
        this.memberId = memberId;
        this.productId = productId;
        this.quantity = quantity;
    }
}
