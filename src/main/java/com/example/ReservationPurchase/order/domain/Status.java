package com.example.ReservationPurchase.order.domain;

import lombok.Getter;

@Getter
public enum Status {
    PRODUCT("product"),
    CANCEL("cancel"),
    RETURN("return"),
    COMPLETE("complete"),
    PROCESSING("processing"),
    RESERVATION_PRODUCT("reservationProduct");

    private final String status;

    Status(final String status) {
        this.status = status;
    }
}
