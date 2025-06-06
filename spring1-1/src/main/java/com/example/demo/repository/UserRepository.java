package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Member;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Member, Long> {

    // 依帳號查詢會員（Optional包裝避免null）
    Optional<Member> findByAccount(String account);

    // 也可以加上是否啟用的查詢
    Optional<Member> findByAccountAndEnabledTrue(String account);
}
