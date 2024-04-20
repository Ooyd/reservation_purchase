package com.example.ReservationPurchase.auth.presentation;

import com.example.ReservationPurchase.auth.application.LoginService;
import com.example.ReservationPurchase.auth.domain.LoginInfo;
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
    public ResponseEntity<String> login(@RequestBody LoginInfo loginInfo) throws Exception {
        String token = loginService.jwtLogin(loginInfo);
        return ResponseEntity.ok(token);
    }
}
