package com.example.ReservationPurchase.auth.presentation;

import com.example.ReservationPurchase.member.application.port.MemberRepository;
import com.example.ReservationPurchase.member.domain.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@DisplayName("통하테스트 [인증/인가]")
class LoginApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    private Member setMember() {
        Member member = Member.builder()
                .email("test@naver.com")
                .password(passwordEncoder.encode("12345678"))
                .name("김이박")
                .build();
        return memberRepository.save(member);
    }

    @DisplayName("로그인 테스트 : 성공")
    @Test
    void 로그인요청() throws Exception {
        // given
        Member saved = setMember();
        String json = """
                {
                  "email" : "user1@naver.com",
                  "password" : "12345678"
                }
                """;

        // when, then
        mockMvc.perform(post("/api/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());
    }

    @DisplayName("로그인 테스트 : 가입되지 않은 이메일로 로그인시 예외발생")
    @Test
    void 가입되지_않은_이메일로_로그인_요청시_예외발생() throws Exception {
        // given
        String json = """
                {
                  "email" : "user1@naver.com",
                  "password" : "12345678"
                }
                """;

        // when, then
        mockMvc.perform(post("/api/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isNotFound());
    }

    @DisplayName("로그인 테스트 : 가입되지 않은 이메일로 로그인시 예외발생")
    @Test
    void 비밀번호가_일치하지_않으면_예외발생() throws Exception {
        // given
        Member saved = setMember();
        String json = """
                {
                  "email" : "user1@naver.com",
                  "password" : "87654321"
                }
                """;

        // when, then
        mockMvc.perform(post("/api/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest());
    }
}