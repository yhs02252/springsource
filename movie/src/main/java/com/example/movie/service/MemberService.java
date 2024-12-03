package com.example.movie.service;

import org.springframework.stereotype.Service;

import com.example.movie.dto.MemberDTO;
import com.example.movie.dto.PasswordDTO;
import com.example.movie.entity.Member;
import com.example.movie.entity.constant.MemberRole;

@Service
public interface MemberService {

    // 닉네임 수정
    void nickNameUpdate(MemberDTO memberDTO);

    // 비밀번호 수정
    void passwordUpdate(PasswordDTO passwordDTO) throws Exception;

    // 회원탈퇴
    void leave(PasswordDTO passwordDTO) throws Exception;

    // 회원가입
    String register(MemberDTO memberDTO);

    default Member dtoToEntity(MemberDTO memberDTO) {
        return Member.builder()
                .email(memberDTO.getEmail())
                .password(memberDTO.getPassword())
                .nickName(memberDTO.getNickName())
                .memberRole(MemberRole.MEMBER)
                .build();
    }

    default MemberDTO entityToDto(Member member) {
        return MemberDTO.builder()
                .mid(member.getMid())
                .email(member.getEmail())
                .password(member.getPassword())
                .nickName(member.getNickName())
                .memberRole(member.getMemberRole())
                .regDate(member.getCreatedDateTime())
                .upDateTime(member.getLastModifiedDateTime())
                .build();
    }

}
