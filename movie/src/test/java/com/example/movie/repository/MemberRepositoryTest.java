package com.example.movie.repository;

import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Commit;

import com.example.movie.entity.Member;
import com.example.movie.entity.Movie;
import com.example.movie.entity.Review;
import com.example.movie.entity.constant.MemberRole;

import jakarta.transaction.Transactional;

@SpringBootTest
public class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void testMemberInsert() {

        // IntStream.rangeClosed(1, 50).forEach(i -> {
        // Member member = Member.builder()
        // .email("user" + i + "@naver.com")
        // .password(passwordEncoder.encode("1111"))
        // .nickName("nickName" + i)
        // .memberRole(MemberRole.MEMBER)
        // .build();
        // memberRepository.save(member);
        // });

        Member member = Member.builder()
                .email("admin@naver.com")
                .password(passwordEncoder.encode("1111"))
                .nickName("adminName")
                .memberRole(MemberRole.ADMIN)
                .build();
        memberRepository.save(member);
    }

    @Test
    public void testMemberUpdate() {
        Member member = memberRepository.findById(1L).get();
        member.setNickName("newNickName");
        memberRepository.save(member);

    }

    @Test
    @Transactional
    public void testMemberUpdateQuery() {
        memberRepository.updateNickName("newnewNickName", "user1@naver.com");

    }

    @Commit
    @Test
    @Transactional
    public void testDeleteQuery() {

        // 리뷰 삭제(멤버를 이용하여 삭제)
        reviewRepository.deleteByMember(Member.builder().mid(1L).build());
        // 회원 삭제
        memberRepository.deleteById(1L);
    }
}
