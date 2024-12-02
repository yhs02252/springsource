package com.example.movie.service;

import org.springframework.stereotype.Service;

import com.example.movie.dto.MemberDTO;
import com.example.movie.dto.PasswordDTO;

@Service
public interface MemberService {

    // 닉네임 수정
    void nickNameUpdate(MemberDTO memberDTO);

    // 비밀번호 수정
    void passwordUpdate(PasswordDTO passwordDTO) throws Exception;

    // 회원가입

}
