package com.example.ReservationPurchase.member.domain.response;

import com.example.ReservationPurchase.member.domain.Member;

import java.util.Optional;

public interface MemberRepository {

    Member save(Member member);

    Optional<Member> findById(Long id);

    Optional<Member> findByEmail(String email);
}
