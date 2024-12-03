package com.example.movie.service;

import java.util.Optional;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.movie.dto.MemberDTO;
import com.example.movie.dto.PasswordDTO;
import com.example.movie.entity.Member;
import com.example.movie.repository.MemberRepository;
import com.example.movie.repository.ReviewRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RequiredArgsConstructor
@Log4j2
@Service
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final ReviewRepository reviewRepository;

    @Override
    @Transactional
    public void nickNameUpdate(MemberDTO memberDTO) {

        memberRepository.updateNickName(memberDTO.getNickName(), memberDTO.getEmail());
    }

    @Override
    public void passwordUpdate(PasswordDTO passwordDTO) throws Exception {
        // email 을 이용해 사용자 찾기
        // Optional<Member> result =
        // memberRepository.findByEmail(passwordDTO.getEmail());
        // if (!result.isPresent())
        // throw new UsernameNotFoundException("이메일 확인");

        Member member = memberRepository.findByEmail(passwordDTO.getEmail()).get();

        // 현재 비밀번호(db에 저장된 값)가 입력 비밀번호(입력값)와 일치하는지 검증
        if (!passwordEncoder.matches(passwordDTO.getCurrentPassword(), member.getPassword())) {
            // false : 되돌려 보내기
            throw new Exception("현재 비밀번호를 확인해주세요");

        } else {
            // true : 새 비밀번호로 수정
            member.setPassword(passwordEncoder.encode(passwordDTO.getChangePassword()));
            memberRepository.save(member);
        }

    }

    @Override
    @Transactional
    public void leave(PasswordDTO passwordDTO) throws Exception {
        Member member = memberRepository.findByEmail(passwordDTO.getEmail()).get();

        if (!passwordEncoder.matches(passwordDTO.getCurrentPassword(), member.getPassword())) {
            // false : 되돌려 보내기
            throw new Exception("현재 비밀번호를 확인해주세요");

        } else {
            reviewRepository.deleteByMember(member);
            memberRepository.deleteById(member.getMid());
        }
    }

    @Override
    public String register(MemberDTO memberDTO) {

        Member member = dtoToEntity(memberDTO);

        member.setPassword(passwordEncoder.encode(member.getPassword()));

        return memberRepository.save(member).getEmail();
    }

}
