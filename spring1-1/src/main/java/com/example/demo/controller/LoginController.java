package com.example.demo.controller;

import com.example.demo.utils.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@RestController
public class LoginController {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @PostMapping("/login")
    public void login(
            @RequestParam String account,
            @RequestParam String password,
            HttpServletResponse response
    ) throws IOException {
        String sql = "SELECT * FROM member WHERE account = :account";
        Map<String, Object> member = null;

        try {
            member = jdbcTemplate.queryForMap(sql, new MapSqlParameterSource("account", account));
        } catch (Exception e) {
            // 使用者不存在
            response.sendRedirect("/login.html?error=notfound");
            return;
        }

        String hashed = (String) member.get("password");
        if (BCrypt.checkpw(password, hashed)) {
            // 登入成功
            response.sendRedirect("/success.html");
        } else {
            // 密碼錯誤
            response.sendRedirect("/login.html?error=wrongpwd");
        }
    }
}