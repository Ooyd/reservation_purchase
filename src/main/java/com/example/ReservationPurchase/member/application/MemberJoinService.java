package com.example.ReservationPurchase.member.application;


import com.example.ReservationPurchase.auth.application.port.RedisMailRepository;
import com.example.ReservationPurchase.member.application.port.MemberRepository;
import com.example.ReservationPurchase.member.domain.MemberCreate;
import com.example.ReservationPurchase.member.domain.Member;
import com.example.ReservationPurchase.member.exception.MemberErrorCode;
import com.example.ReservationPurchase.member.exception.MemberException;
import com.example.ReservationPurchase.common.util.CryptoUtil;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class MemberJoinService {
    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final RedisMailRepository redisMailRepository;


    @Autowired
    private CryptoUtil cryptoUtil;
    public MemberJoinService(final MemberRepository memberRepository, final BCryptPasswordEncoder passwordEncoder, final RedisMailRepository redisMailRepository) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
        this.redisMailRepository = redisMailRepository;
    }

    @Transactional
    public MemberJoinResponse join(MemberCreate memberCreate) throws Exception{
        memberCreate.validate();

        checkDuplicatedEmail(cryptoUtil.encrypt(memberCreate.getEmail()));

        checkAuthenticNumber(memberCreate);

        Member member = Member.create(memberCreate);

        encodePassword(member);

        twoWayEncode(member);

        Member saved = memberRepository.save(member);

        String decodedEmail = cryptoUtil.decrypt(saved.getEmail());

        return MemberJoinResponse.from(saved,decodedEmail);
    }

    private void checkDuplicatedEmail(String email) {
        memberRepository.findByEmail(email).ifPresent(it -> {
            throw new MemberException.MemberDuplicatedException(MemberErrorCode.MEMBER_DUPLICATED);
        });
    }

    private void encodePassword(Member member) {
        String encodedPassword = passwordEncoder.encode(member.getPassword());
        member.applyEncodedPassword(encodedPassword);
    }
    private void twoWayEncode(Member member) throws Exception {
        String encryptedEmail = cryptoUtil.encrypt(member.getEmail());
        member.applyEncodedEmail(encryptedEmail);
        String encryptedName = cryptoUtil.encrypt(member.getName());
        member.applyEncodedName(encryptedName);
        String encryptedAddress = cryptoUtil.encrypt(member.getAddress());
        member.applyEncodedAddress(encryptedAddress);
    }

    private void checkAuthenticNumber(final MemberCreate memberCreate) {
        String authenticationNumber = redisMailRepository.getData(memberCreate.getEmail());
        memberCreate.checkAuthenticated(authenticationNumber);

        redisMailRepository.deleteData(memberCreate.getEmail());
    }
}
