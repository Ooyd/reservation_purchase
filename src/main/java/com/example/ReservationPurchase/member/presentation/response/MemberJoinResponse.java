package com.example.ReservationPurchase.member.presentation.response;

import com.example.ReservationPurchase.member.domain.Member;
import lombok.Getter;

@Getter
public class MemberJoinResponse {

    private final Long id;
    private final String email;

    public MemberJoinResponse(final Long id, final String email) {
        this.id = id;
        this.email = email;
    }

    public static MemberJoinResponse from(final Member saved) {
        return new MemberJoinResponse(
                saved.getId(),
                saved.getEmail()
        );
    }
}
