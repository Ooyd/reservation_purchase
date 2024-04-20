package com.example.ReservationPurchase.member.application;

import com.example.ReservationPurchase.auth.application.port.RefreshRepository;
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

import java.util.Map;

@Service
public class MemberUpdateService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final RefreshRepository refreshRepository;


    public MemberUpdateService(final MemberRepository memberRepository, final BCryptPasswordEncoder passwordEncoder, RefreshRepository refreshRepository) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
        this.refreshRepository = refreshRepository;
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

        // 비밀번호 업데이트 시 모든 기기에서 로그아웃
        // Id에 해당하는 refresh 토큰 확인 후 제거
        String memberId = String.valueOf(member.getId());
        Map<String, String> allDevice = refreshRepository.getAllFromHash(memberId);
        for (String uuid : allDevice.keySet()) {
            refreshRepository.delete(memberId + "-" + uuid);
            refreshRepository.delete(uuid);
            refreshRepository.removeFromHash(memberId, uuid);
        }

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
