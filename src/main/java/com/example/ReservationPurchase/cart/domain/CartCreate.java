package com.example.ReservationPurchase.cart.domain;

import com.example.ReservationPurchase.cart.exception.CartErrorCode;
import com.example.ReservationPurchase.cart.exception.CartException;
import com.example.ReservationPurchase.product.domain.Product;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
public class CartCreate {
    private static final int MAX_PRODUCT_QUANTITY = 5000;

    private Long memberId;

    private Product product;

    private int quantity;

    @Builder
    public CartCreate(Long memberId, Product product, int quantity) {
        this.memberId = memberId;
        this.product = product;
        this.quantity = quantity;
    }

    public void validate() {
        if(this.quantity > MAX_PRODUCT_QUANTITY) {
            throw new CartException.CartOverQuantityException(CartErrorCode.CART_OVER_QUANTITY);
        }
    }
}
