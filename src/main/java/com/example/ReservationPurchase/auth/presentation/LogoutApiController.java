package com.example.ReservationPurchase.auth.presentation;

import com.example.ReservationPurchase.auth.application.LogoutService;
import com.example.ReservationPurchase.auth.domain.LogoutInfo;
import com.example.ReservationPurchase.auth.domain.UserDetailsImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth/logout")
public class LogoutApiController {

    private final LogoutService logoutService;

    public LogoutApiController(final LogoutService logoutService) {
        this.logoutService = logoutService;
    }

    @PostMapping
    public ResponseEntity<Void> logout(
            @RequestBody LogoutInfo logoutInfo,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        logoutService.logout(logoutInfo, userDetails.getEmail());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/all-device")
    public ResponseEntity<Void> logoutAll(
            @RequestBody LogoutInfo logoutInfo,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        logoutService.logoutAll(logoutInfo, userDetails.getEmail());
        return ResponseEntity.ok().build();
    }
}
