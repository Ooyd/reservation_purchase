package com.example.ReservationPurchase.member.application;

import com.example.ReservationPurchase.member.application.port.MemberRepository;
import com.example.ReservationPurchase.member.application.port.ProfileRepository;
import com.example.ReservationPurchase.member.domain.Member;
import com.example.ReservationPurchase.member.exception.MemberErrorCode;
import com.example.ReservationPurchase.member.exception.MemberException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ProfileService {

    private final MemberRepository memberRepository;
    private final ProfileRepository profileRepository;

    public ProfileService(final MemberRepository memberRepository, final ProfileRepository profileRepository) {
        this.memberRepository = memberRepository;
        this.profileRepository = profileRepository;
    }


    @Transactional
    public String upload(final Long memberId, final Long principalId, final MultipartFile file) {

        if (!memberId.equals(principalId)) {
            throw new IllegalArgumentException("등록은 본인 계정에 한하여 가능합니다.");
        }

        if (file == null) {
            throw new IllegalArgumentException("등록할 파일이 존재하지 않습니다.");
        }

        Member member = memberRepository.findById(principalId).orElseThrow(() ->
                new MemberException.MemberNotFoundException(MemberErrorCode.MEMBER_NOT_FOUND));

        // 이미 등록된 프로필 이미지가 있다면, 삭제한다.
        if (member.isProfileUploaded()) {
            profileRepository.delete(member.getProfileUrl());
        }

        // 새로운 프로필 이미지 등록
        String savedUrl = profileRepository.upload(file);

        // 등록된 경로 저장
        member.saveProfile(savedUrl);
        memberRepository.save(member);

        return savedUrl;
    }
}
