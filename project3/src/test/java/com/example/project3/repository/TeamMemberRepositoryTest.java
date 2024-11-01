package com.example.project3.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.project3.entity.Member;
import com.example.project3.entity.Team;

import jakarta.transaction.Transactional;
import oracle.net.aso.l;

@SpringBootTest
public class TeamMemberRepositoryTest {

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    public void createTeamTest() {

        Team team1 = teamRepository.findById("team1").get();
        Team team2 = Team.builder().id("team2").build();

        IntStream.rangeClosed(1, 5).forEach(i -> {
            Member member = Member.builder()
                    .id("user" + i)
                    .userName("홍길동" + i)
                    .team(team1)
                    .build();

            memberRepository.save(member);
        });

        IntStream.rangeClosed(6, 10).forEach(i -> {
            Member member = Member.builder()
                    .id("user" + i)
                    .userName("성춘향" + i)
                    .team(team2)
                    .build();

            memberRepository.save(member);
        });
    }

    @Test
    public void createMemberTest() {

        // 직접 가져오기
        // Team team1 = teamRepository.findById("team1").get();

        // builder 활용하기
        Team team2 = Team.builder().id("team2").build();

        Member member = Member.builder()
                .id("user2")
                .userName("성춘향")
                .team(team2)
                .build();
        memberRepository.save(member);
    }

    @Test
    public void selectTest() {

        // 회원 찾기
        System.out.println(memberRepository.findById("user1").get());

        Member member = memberRepository.findById("user6").get(); // id가 "user2"인 멤버의 정보 담기
        System.out.println(member);

        // 팀 정보
        System.out.println(member.getTeam()); // 팀 정보 전체 찾기
        System.out.println(member.getTeam().getName()); // 팀 정보 -> 팀 이름 찾기
    }

    @Test
    public void selectAllTest() {

        memberRepository.findAll().forEach(member -> System.out.println(member));
    }

    @Test
    public void memberEqualTeamTest() {

        memberRepository.findByEqualTeam("팀1").forEach(m -> System.out.println(m));
    }

    @Test
    public void updateTest() {
        // user6 의 팀 변경하기 team 1로
        Member member = memberRepository.findById("user6").get();

        Team team1 = teamRepository.findById("team1").get();
        member.setTeam(team1);
        memberRepository.save(member);
    }

    @Test
    public void deleteTest() {
        // team2 제거 순서

        // 외래키 제약조건에서는 자식부터 변경 필요 (자식 처리 -> 부모 처리)
        Team team2 = Team.builder().id("team2").build();
        List<Member> members = memberRepository.findByTeam(team2);
        members.forEach(member -> System.out.println(member));

        // team member 삭제
        members.forEach(member -> memberRepository.delete(member));
        // // team 처리
        teamRepository.deleteById("team2");

        // team2 멤버를 team1 으로 변경
        Team team1 = teamRepository.findById("team1").get();

        members.forEach(member -> {
            member.setTeam(team1);
            memberRepository.save(member);
        });
        // team2 처리
        teamRepository.deleteById("team2");

    }

    // member 삽입하면서 team 삽입이 가능한가?
    // sql 1) 부모 삽입 2) 자식 삽입
    // jpa 에서는 객체 형태로 다루니까

    @Test
    public void memberAndTeamInsertTest() {

        // EntityNotFoundException -> cascade 설정이 없는 경우

        Team team = Team.builder().id("team3").name("팀3").build();
        // teamRepository.save(team) -> 생략할 수 있는 방법
        Member member = Member.builder().id("user11").userName("홍길동").team(team).build();

        memberRepository.save(member);
    }

    @Test
    public void memberAndTeamUpdateTest() {

        Team team = teamRepository.findById("team3").get();
        team.setName("victory");
        // teamRepository.save(team)

        Member member = Member.builder().id("user11").userName("홍길동").team(team).build();

        memberRepository.save(member);
    }

    @Test
    public void selectTeamTest() {

        // 팀 찾기
        Team team1 = teamRepository.findById("team1").get();

        team1.getMembers().forEach(member -> {
            // 팀 정보 출력해보기
            System.out.println(member);

            // 팀에 속한 멤버 정보 출력해보기
            System.out.println(member.getUserName());
        });
    }

    // 양방향
    @Test
    @Transactional // <-
    public void selectMemberTest() {
        // 팀 찾기
        Team team1 = teamRepository.findById("team1").get();

        // left join 하지 않음 => member 정보 없음
        team1.getMembers().forEach(member -> {

            // 멤버정보 전체 확인
            System.out.println(member);

            // 멤버 정보 -> 멤버 이름 확인
            System.out.println(member.getUserName());
        });
    }

    // 팀 기준 멤버 삽입
    @Test
    public void teamAndMemberInsertTest() {

        // EntityNotFoundException -> cascade 설정이 없는 경우

        Team team = Team.builder().id("team3").name("팀3").build();
        // teamRepository.save(team) -> 생략할 수 있는 방법
        Member member = Member.builder().id("user12").userName("수선화").team(team).build();
        team.getMembers().add(member);
        teamRepository.save(team);
    }

    @Test
    public void selectByUserNameTest() {
        // IntStream.rangeClosed(6, 10).forEach(i -> {
        // List<Member> members = memberRepository.findByUserName("성춘향" + i);
        // System.out.println(members);
        // });

        // IntStream memStream = IntStream.rangeClosed(6, 10);
        // memStream.forEach(i -> {
        // memberRepository.findByUserName("성춘향" + i);
        // });
        // System.out.println(memStream); 정보가 안나옴
    }
}
