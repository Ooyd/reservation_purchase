package com.example.ReservationPurchase.member.application.port;

import com.example.ReservationPurchase.member.domain.Member;
import java.util.Optional;

public interface MemberRepository {
    Member save(Member member);

    Optional<Member> findByEmail(String email);

    Optional<Member> findById(Long id);
}
