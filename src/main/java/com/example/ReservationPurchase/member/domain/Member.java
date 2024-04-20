package com.example.ReservationPurchase.member.domain;

import lombok.Builder;
import lombok.Getter;
import java.time.LocalDateTime;

@Getter
public class Member {

    private Long id;
    private String email;
    private String password;
    private String name;
    private String address;
    private String phone;
    private String profileUrl;
    protected LocalDateTime createdAt;
    protected LocalDateTime updatedAt;

    @Builder
    public Member(final Long id, final String email, final String password, final String name, final String address, final String phone,
                  final String profileUrl, final LocalDateTime createdAt, final LocalDateTime updatedAt) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.profileUrl = profileUrl;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static Member create(final MemberCreate memberCreate) {
        return Member.builder()
                .email(memberCreate.getEmail())
                .password(memberCreate.getPassword())
                .name(memberCreate.getName())
                .address(memberCreate.getAddress())
                .phone(memberCreate.getPhone())
                .build();
    }

    public void applyEncodedPassword(final String encodedPassword) {
        this.password = encodedPassword;
    }

    public void applyEncodedEmail(final String encodedEmail) {
        this.email = encodedEmail;
    }
    public void applyEncodedName(final String encodedName) {
        this.name = encodedName;
    }
    public void applyEncodedAddress(final String encodedAddress) {
        this.address = encodedAddress;
    }


    public void saveProfile(final String profileUrl) {
        this.profileUrl = profileUrl;
    }

    public boolean isProfileUploaded() {
        return this.profileUrl != null;
    }

    public void update(final MemberUpdate memberUpdate) {
        this.name = memberUpdate.getName();
        this.address = memberUpdate.getAddress();
        this.phone = memberUpdate.getPhone();
    }
}