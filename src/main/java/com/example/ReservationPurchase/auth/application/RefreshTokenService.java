package com.example.ReservationPurchase.auth.application;

import com.example.ReservationPurchase.auth.application.port.RefreshRepository;
import com.example.ReservationPurchase.auth.domain.RefreshTokenInfo;
import com.example.ReservationPurchase.auth.presentation.response.RefreshResponse;
import com.example.ReservationPurchase.exception.GlobalException;
import com.example.ReservationPurchase.member.application.port.MemberRepository;
import com.example.ReservationPurchase.member.domain.Member;
import com.example.ReservationPurchase.member.exception.MemberErrorCode;
import com.example.ReservationPurchase.member.exception.MemberException.MemberNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class RefreshTokenService {

    private final JWTTokenProvider jwtTokenProvider;
    private final MemberRepository memberRepository;
    private final RefreshRepository refreshRepository;

    public RefreshTokenService(final JWTTokenProvider jwtTokenProvider, final MemberRepository memberRepository, final RefreshRepository refreshRepository) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.memberRepository = memberRepository;
        this.refreshRepository = refreshRepository;
    }

    public RefreshResponse refresh(final RefreshTokenInfo refreshTokenInfo) {
        String refreshToken = refreshTokenInfo.getRefreshToken();

        // 1. refresh저장소에 해당 refresh토큰이 존재하는지 확인한다.
        String memberId = refreshRepository.findByValue(refreshTokenInfo.getRefreshToken());
        if (memberId == null) {
            throw new GlobalException(HttpStatus.NOT_FOUND, "[ERROR] not found refresh token");
        }

        // 2. refresh로부터 member정보를 꺼내 DB에서 가져온다.
        String email = jwtTokenProvider.getEmail(refreshToken);
        Member member = memberRepository.findByEmail(email).orElseThrow(() ->
                new MemberNotFoundException(MemberErrorCode.MEMBER_NOT_FOUND));

        // 3. AccessToken을 발급하여 기존 RefreshToken과 함께 응답한다.
        String accessToken = jwtTokenProvider.generateAccess(email, member.getName());

        // 리프레쉬 기간이 얼마 안남으면 그냥 리프레쉬도 새로 발급해준다.(보류)
        return RefreshResponse.from(accessToken, refreshToken);
    }
}
