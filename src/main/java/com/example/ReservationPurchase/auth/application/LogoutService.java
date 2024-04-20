package com.example.ReservationPurchase.auth.application;

import com.example.ReservationPurchase.auth.application.port.RefreshRepository;
import com.example.ReservationPurchase.auth.domain.LogoutInfo;
import com.example.ReservationPurchase.auth.exception.AuthErrorCode;
import com.example.ReservationPurchase.auth.exception.AuthException.UnauthorizedException;
import com.example.ReservationPurchase.exception.GlobalException;
import com.example.ReservationPurchase.member.application.port.MemberRepository;
import com.example.ReservationPurchase.member.domain.Member;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
public class LogoutService {

    private final JWTTokenProvider jwtTokenProvider;
    private final RefreshRepository refreshRepository;
    private final MemberRepository memberRepository;

    public LogoutService(final JWTTokenProvider jwtTokenProvider, final RefreshRepository refreshRepository, MemberRepository memberRepository) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.refreshRepository = refreshRepository;
        this.memberRepository = memberRepository;
    }

    /**
     * 로그아웃
     */
    @Transactional
    public void logout(final LogoutInfo logoutInfo, final String principalEmail) {
        String refreshToken = logoutInfo.getRefreshToken();

        String email = jwtTokenProvider.getEmail(refreshToken);

        checkAuthorized(email, principalEmail);

        // 존재하는 리프레쉬 토큰 확인 후 제거
        String memberId = refreshRepository.findByValue(refreshToken);
        if (memberId == null) {
            throw new GlobalException(HttpStatus.NOT_FOUND, "[ERROR] not found refresh token");
        }

        refreshRepository.delete(refreshToken);
    }

    public void logoutAll(final LogoutInfo logoutInfo, final String principalEmail) {
        String refreshToken = logoutInfo.getRefreshToken();

        String email = jwtTokenProvider.getEmail(refreshToken);

        checkAuthorized(email, principalEmail);

        Member member = memberRepository.findByEmail(email).orElseThrow(() ->
                new GlobalException(HttpStatus.NOT_FOUND, "[ERROR] User not found"));

        // 존재하는 모든 리프레쉬 토큰 확인 후 제거
        String memberId = String.valueOf(member.getId());
        // 존재하는 모든 uuid - refreshToken 가져오기
        Map<String, String> allDevice = refreshRepository.getAllFromHash(memberId);
        for (String uuid : allDevice.keySet()) {
            refreshRepository.delete(memberId + "-" + uuid);
            refreshRepository.delete(uuid);
            refreshRepository.removeFromHash(memberId, uuid);
        }
    }

    private void checkAuthorized(String email, String principalEmail) {
        if (!email.equals(principalEmail)) {
            throw new UnauthorizedException(AuthErrorCode.UNAUTHORIZED_ERROR);
        }
    }
}
