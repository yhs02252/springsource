package com.example.mart.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.mart.entitty.item.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {

}
