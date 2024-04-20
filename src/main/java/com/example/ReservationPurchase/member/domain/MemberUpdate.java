package com.example.ReservationPurchase.member.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberUpdate {

    private String name;

    private String address;

    private String phone;

    @Builder
    public MemberUpdate(final String name,final String address,final String phone) {
        this.name = name;
        this.address = address;
        this.phone = phone;
    }

}
