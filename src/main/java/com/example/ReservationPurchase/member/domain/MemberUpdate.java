package com.example.ReservationPurchase.member.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberUpdate {

    private String name;

    @Builder
    public MemberUpdate(final String name) {
        this.name = name;
    }

}
