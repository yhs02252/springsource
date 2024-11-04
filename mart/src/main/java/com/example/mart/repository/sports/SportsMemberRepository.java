package com.example.mart.repository.sports;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.mart.entitty.sports.Member;

public interface SportsMemberRepository extends JpaRepository<Member, Long> {

}