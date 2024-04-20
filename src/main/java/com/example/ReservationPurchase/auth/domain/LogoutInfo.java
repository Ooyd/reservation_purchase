package com.example.ReservationPurchase.auth.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LogoutInfo {

    private String refreshToken;

    public LogoutInfo(final String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
