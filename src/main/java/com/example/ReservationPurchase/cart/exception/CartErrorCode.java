package com.example.ReservationPurchase.cart.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum CartErrorCode {
    CART_OVER_QUANTITY(HttpStatus.BAD_REQUEST,"[ERROR] 수량은 최대 5000 입니다."),
    CART_DUPLICATED(HttpStatus.CONFLICT,"[ERROR] 동일한 제품이 이미 존재합니다."),
    UNAUTHORIZED_ACCESS_ERROR(HttpStatus.UNAUTHORIZED, "[ERROR] 권한이 없습니다."),
    NOT_FOUND_PRODUCT(HttpStatus.NOT_FOUND, "[ERROR] 조회할 장바구니 제품이 없습니다.");

    private final HttpStatus status;
    private final String message;

    CartErrorCode(final HttpStatus status, final String message) {
        this.status = status;
        this.message = message;
    }
}
