package com.example.demo.service;

import com.example.demo.entity.Member;
import com.example.demo.entity.RegistrationRequest;
import com.example.demo.entity.VerificationToken;
import com.example.demo.repository.MemberRepository;
import com.example.demo.repository.VerificationTokenRepository;
import com.example.demo.utils.BCrypt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private VerificationTokenRepository tokenRepository;

    @Autowired
    private EmailService emailService;

    public void setMember(RegistrationRequest request) {
        // 1. 檢查帳號是否存在
        if (memberRepository.findByAccount(request.getAccount()) != null) {
            throw new IllegalArgumentException("帳號已存在");
        }

        // 2. 建立 Member（enabled = false）
        Member member = new Member();
        member.setAccount(request.getAccount());
        member.setPassword(BCrypt.hashpw(request.getPassword(), BCrypt.gensalt())); // 密碼加密
        member.setCname(request.getCname());
        member.setEnabled(false); // 尚未驗證

        // 3. 儲存 Member 到資料庫
        memberRepository.save(member);

        // 4. 建立驗證 Token（不需要 payload）
        String token = UUID.randomUUID().toString();
        VerificationToken vt = new VerificationToken();
        vt.setToken(token);
        vt.setMember(member);
        vt.setExpiryDate(LocalDateTime.now().plusHours(24));

        // 5. 儲存 Token
        tokenRepository.save(vt);

        // 6. 寄送驗證信
        String verifyUrl = "http://localhost:8080/api/verification?token=" + token;
        String subject = "請驗證您的 Email 帳號";
        String body = "親愛的使用者您好：\n\n請點擊以下連結完成註冊驗證：\n" + verifyUrl + "\n\n此連結將在24小時後失效。";

        boolean success = emailService.sendSimpleMessage(request.getAccount(), subject, body);
        if (!success) {
            throw new RuntimeException("信件寄送失敗，請稍後再試");
        }
    }
}