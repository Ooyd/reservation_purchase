package com.example.ReservationPurchase.auth.application;

import com.example.ReservationPurchase.auth.domain.LoginInfo;
import com.example.ReservationPurchase.auth.exception.AuthErrorCode;
import com.example.ReservationPurchase.auth.exception.AuthException;
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
    private final CryptoUtil cryptoUtil;

    public LoginService(MemberRepository memberRepository, JWTTokenProvider jwtTokenProvider, BCryptPasswordEncoder passwordEncoder, CryptoUtil cryptoUtil) {
        this.memberRepository = memberRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.passwordEncoder = passwordEncoder;
        this.cryptoUtil = cryptoUtil;
    }

    public String jwtLogin(LoginInfo loginInfo) throws Exception {
        String email = loginInfo.getEmail();
        String password = loginInfo.getPassword();


        String encryptedEmail = cryptoUtil.encrypt(email);

        Member member = memberRepository.findByEmail(encryptedEmail).orElseThrow(() ->
                new MemberException.MemberNotFoundException(MemberErrorCode.MEMBER_NOT_FOUND));

        if (isPasswordMatching(password, member)) {
            return jwtTokenProvider.generate(member.getEmail(), member.getName());
        }

        throw new AuthException.InvalidPasswordException(AuthErrorCode.INVALID_PASSWORD_ERROR);
    }

    private boolean isPasswordMatching(String password, Member member) {
        return passwordEncoder.matches(password, member.getPassword());
    }


}
