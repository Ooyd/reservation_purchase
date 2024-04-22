package com.example.ReservationPurchase.order.domain;

import lombok.Getter;

@Getter
public enum Status {
    PROCESSING("product"),
    COMPLETED("reservationProduct"),
    CANCELED("canceled");

    private final String status;

    Status(final String status) {
        this.status = status;
    }
}
