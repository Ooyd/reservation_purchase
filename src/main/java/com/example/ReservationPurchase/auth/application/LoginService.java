package com.example.ReservationPurchase.auth.application;

import com.example.ReservationPurchase.auth.application.port.RefreshRepository;
import com.example.ReservationPurchase.auth.domain.LoginInfo;
import com.example.ReservationPurchase.auth.exception.AuthErrorCode;
import com.example.ReservationPurchase.auth.exception.AuthException;
import com.example.ReservationPurchase.auth.presentation.response.LoginResponse;
import com.example.ReservationPurchase.member.application.port.MemberRepository;
import com.example.ReservationPurchase.member.domain.Member;
import com.example.ReservationPurchase.member.exception.MemberErrorCode;
import com.example.ReservationPurchase.member.exception.MemberException;
import com.example.ReservationPurchase.common.util.CryptoUtil;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    private final MemberRepository memberRepository;
    private final JWTTokenProvider jwtTokenProvider;
    private final BCryptPasswordEncoder passwordEncoder;
    private final RefreshRepository refreshRepository;

    private final CryptoUtil cryptoUtil;

    public LoginService(MemberRepository memberRepository, JWTTokenProvider jwtTokenProvider, BCryptPasswordEncoder passwordEncoder, RefreshRepository refreshRepository, CryptoUtil cryptoUtil) {
        this.memberRepository = memberRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.passwordEncoder = passwordEncoder;
        this.refreshRepository = refreshRepository;
        this.cryptoUtil = cryptoUtil;
    }

    public LoginResponse login(LoginInfo loginInfo) throws Exception {
        String email = loginInfo.getEmail();
        String password = loginInfo.getPassword();

        Member member = findMember(cryptoUtil.encrypt(email));

        checkPassword(password, member);

        String accessToken = jwtTokenProvider.generateAccess(member.getEmail(), member.getName());
        String refreshToken = jwtTokenProvider.generateRefresh(member.getEmail(), member.getName());

        long duration = jwtTokenProvider.getExpiredTime(refreshToken);
        refreshRepository.save(refreshToken, member.getId(), duration);

        return LoginResponse.from(accessToken, refreshToken);
    }

    private Member findMember(String encryptedEmail) {
        return memberRepository.findByEmail(encryptedEmail).orElseThrow(() ->
                new MemberException.MemberNotFoundException(MemberErrorCode.MEMBER_NOT_FOUND));
    }

    private boolean isInvalidPassword(String password, Member member) {
        return !passwordEncoder.matches(password, member.getPassword());
    }

    private void checkPassword(String password, Member member) {
        if (isInvalidPassword(password, member)) {
            throw new AuthException.InvalidPasswordException(AuthErrorCode.INVALID_PASSWORD_ERROR);
        }
    }

    private Member findExistMember(final String email) {
        return memberRepository.findByEmail(email).orElseThrow(() ->
                new MemberException.MemberNotFoundException(MemberErrorCode.MEMBER_NOT_FOUND));
    }



}
