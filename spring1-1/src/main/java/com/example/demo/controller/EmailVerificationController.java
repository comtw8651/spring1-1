package com.example.demo.controller;

import com.example.demo.entity.Member;
import com.example.demo.entity.VerificationToken;
import com.example.demo.repository.MemberRepository;
import com.example.demo.repository.VerificationTokenRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api")
public class EmailVerificationController {

    @Autowired
    private VerificationTokenRepository tokenRepository;

    @Autowired
    private MemberRepository memberRepository;

    @GetMapping("/verification")
    public ResponseEntity<String> verifyEmail(@RequestParam("token") String token) {
        VerificationToken vt = tokenRepository.findByToken(token);

        if (vt == null) {
            return ResponseEntity.badRequest().body("無效的驗證連結");
        }

        if (vt.getExpiryDate().isBefore(LocalDateTime.now())) {
            return ResponseEntity.badRequest().body("驗證連結已過期");
        }

        Member member = vt.getMember();

        if (member.isEnabled()) {
            return ResponseEntity.ok("帳號已驗證，請直接登入");
        }

        member.setEnabled(true);
        memberRepository.save(member);

        tokenRepository.delete(vt); // 驗證後刪除 token

        return ResponseEntity.ok("驗證成功，帳號已啟用，請登入");
    
    }
}