package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Member findByAccount(String account); // 用 email 查找

    boolean existsByAccount(String account); // 檢查帳號是否存在
}

