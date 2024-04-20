package com.example.ReservationPurchase.member.application;

import com.example.ReservationPurchase.member.application.port.MemberRepository;
import com.example.ReservationPurchase.member.domain.Member;
import com.example.ReservationPurchase.member.domain.MemberUpdate;
import com.example.ReservationPurchase.member.domain.PasswordUpdate;
import com.example.ReservationPurchase.member.exception.MemberErrorCode;
import com.example.ReservationPurchase.member.exception.MemberException.MemberNotFoundException;
import com.example.ReservationPurchase.member.exception.MemberException.MemberUnauthorizedException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MemberUpdateService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public MemberUpdateService(final MemberRepository memberRepository, final BCryptPasswordEncoder passwordEncoder) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void update(final MemberUpdate memberUpdate, final Long targetId, final Long principalId) {

        checkAuthorized(targetId, principalId);

        Member member = findExistMember(targetId);

        member.update(memberUpdate);

        memberRepository.save(member);
    }

    @Transactional
    public void updatePassword(final PasswordUpdate passwordUpdate, final Long targetId, final Long principalId) {

        checkAuthorized(targetId, principalId);

        Member member = findExistMember(targetId);

        passwordUpdate.validate();

        String encodedPassword = passwordEncoder.encode(passwordUpdate.getPassword());
        member.applyEncodedPassword(encodedPassword);

        memberRepository.save(member);
    }

    private void checkAuthorized(Long targetId, Long principalId) {
        if (targetId != principalId) {
            throw new MemberUnauthorizedException(MemberErrorCode.UNAUTHORIZED_ACCESS_ERROR);
        }
    }

    private Member findExistMember(Long id) {
        return memberRepository.findById(id).orElseThrow(() ->
                new MemberNotFoundException(MemberErrorCode.MEMBER_NOT_FOUND));

    }

}
