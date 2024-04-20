package com.example.ReservationPurchase.member.presentation.response;

import com.example.ReservationPurchase.member.domain.Member;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class MemberResponse {

    private Long id;
    private String email;
    private String name;
    protected LocalDateTime createdAt;

    @Builder
    public MemberResponse(final Long id, final String email, final String name, final LocalDateTime createdAt) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.createdAt = createdAt;
    }

    public static MemberResponse from(Member member) {
        return MemberResponse.builder()
                .id(member.getId())
                .email(member.getEmail())
                .name(member.getName())
                .createdAt(member.getCreatedAt())
                .build();
    }
}
