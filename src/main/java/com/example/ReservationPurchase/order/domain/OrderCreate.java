package com.example.ReservationPurchase.order.domain;


import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OrderCreate {

    private Long productId;
    private String productType;
    private Integer quantity;
    private Integer price;
    private Long memberId;
    private String address;

    public OrderCreate(
            final Long productId,
            final String productType,
            final Integer quantity,
            final Integer price,
            final Long memberId,
            final String address
    ) {
        this.productId = productId;
        this.productType = productType;
        this.quantity = quantity;
        this.price = price;
        this.memberId = memberId;
        this.address = address;
    }
}
