package com.example.board.service;

import com.example.board.dto.MemberDTO;
import com.example.board.entity.Member;

public interface BoardUserService {

    // 회원가입
    String register(MemberDTO mDto);

    // entity => dto
    default MemberDTO entityToDto(Member member) {
        return MemberDTO.builder()
                .email(member.getEmail())
                .name(member.getName())
                .password(member.getPassword())
                .role(member.getRole())
                .build();
    }

    // dto => entity
    default Member dtoToEntity(MemberDTO mDto) {
        return Member.builder()
                .email(mDto.getEmail())
                .name(mDto.getName())
                .password(mDto.getPassword())
                .role(mDto.getRole())
                .build();
    }
}
