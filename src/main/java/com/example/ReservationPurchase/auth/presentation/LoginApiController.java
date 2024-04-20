package com.example.ReservationPurchase.auth.presentation;

import com.example.ReservationPurchase.auth.application.LoginService;
import com.example.ReservationPurchase.auth.domain.LoginInfo;
import com.example.ReservationPurchase.auth.presentation.response.LoginResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/login")
public class LoginApiController {

    private final LoginService loginService;

    public LoginApiController(final LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping
    public ResponseEntity<LoginResponse> login(@RequestBody LoginInfo loginInfo) throws Exception {
        LoginResponse loginResponse = loginService.login(loginInfo);
        return ResponseEntity.ok(loginResponse);
    }
}
