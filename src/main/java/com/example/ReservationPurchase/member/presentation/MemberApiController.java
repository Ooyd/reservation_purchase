package com.example.ReservationPurchase.member.presentation;

import com.example.ReservationPurchase.auth.domain.UserDetailsImpl;
import com.example.ReservationPurchase.member.application.MemberJoinService;
import com.example.ReservationPurchase.member.application.MemberReadService;
import com.example.ReservationPurchase.member.application.MemberUpdateService;
import com.example.ReservationPurchase.member.application.ProfileService;
import com.example.ReservationPurchase.member.domain.Member;
import com.example.ReservationPurchase.member.domain.MemberCreate;
import com.example.ReservationPurchase.member.domain.MemberUpdate;
import com.example.ReservationPurchase.member.domain.PasswordUpdate;
import com.example.ReservationPurchase.member.presentation.response.MemberJoinResponse;
import com.example.ReservationPurchase.member.presentation.response.MemberResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/members")
public class MemberApiController {

    private final MemberReadService memberReadService;
    private final MemberJoinService memberJoinService;
    private final MemberUpdateService memberUpdateService;
    private final ProfileService profileService;

    public MemberApiController(final MemberReadService memberReadService, final MemberJoinService memberJoinService, final MemberUpdateService memberUpdateService, final ProfileService profileService) {
        this.memberReadService = memberReadService;
        this.memberJoinService = memberJoinService;
        this.memberUpdateService = memberUpdateService;
        this.profileService = profileService;
    }

    @PostMapping
    public ResponseEntity<MemberJoinResponse> join(@RequestBody final MemberCreate memberCreate) throws Exception {
        return ResponseEntity.ok(memberJoinService.join(memberCreate));
    }

    @PostMapping("/{id}/profile")
    public ResponseEntity<String> uploadProfile(@PathVariable("id") final Long memberId,
                                                @RequestParam("file") MultipartFile multipartFile,
                                                @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok(profileService.upload(memberId, userDetails.getId(), multipartFile));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> update(@RequestBody final MemberUpdate memberUpdate,
                                       @PathVariable("id") final Long id,
                                       @AuthenticationPrincipal UserDetailsImpl userDetails) {
        memberUpdateService.update(memberUpdate, id, userDetails.getId());
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}/password")
    public ResponseEntity<Void> updatePassword(@RequestBody final PasswordUpdate passwordUpdate,
                                               @PathVariable("id") final Long id,
                                               @AuthenticationPrincipal UserDetailsImpl userDetails) {
        memberUpdateService.updatePassword(passwordUpdate, id, userDetails.getId());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<MemberResponse> read(@PathVariable("id") final Long id,
                                               @AuthenticationPrincipal UserDetailsImpl userDetails) {
        Member member = memberReadService.read(id, userDetails.getId());
        return ResponseEntity.ok(MemberResponse.from(member));
    }
}
