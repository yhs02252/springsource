package com.example.club.service;

import com.example.club.dto.ClubMemberDTO;

public interface ClubUserService {

    // 회원 가입
    String register(ClubMemberDTO dto);
}
