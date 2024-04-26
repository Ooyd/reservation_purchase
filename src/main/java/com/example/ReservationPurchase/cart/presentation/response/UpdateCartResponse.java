package com.example.ReservationPurchase.cart.presentation.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UpdateCartResponse {
    private Long cartid;
    private int productQuantity;
    private LocalDateTime updatedAt;

    @Builder
    public UpdateCartResponse(Long cartid, int productQuantity, LocalDateTime updatedAt) {
        this.cartid = cartid;
        this.productQuantity = productQuantity;
        this.updatedAt = updatedAt;
    }

}
