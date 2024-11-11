package com.example.jpql.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import com.example.jpql.jpql.Member;
import com.example.jpql.jpql.Team;

public interface JpqlMemberRepository extends JpaRepository<Member, Long>, QuerydslPredicateExecutor<Member> {

    // findAll() = @Qeury 로 직접 구현
    // @Query("SELECT m FROM Member m")

    // 정렬과 동시에 출력
    @Query("SELECT m FROM Member m")
    List<Member> findAllMembers(Sort sort);

    // SELECT 다음에 오는 구문이 2개 이상인 경우 List 상태인 Entity타입으로 받을 수 없음
    @Query("SELECT m.name, m.age FROM Member m")
    // List<Member> findMembers2();
    List<Object[]> findMembers2();

    // 쿼리 메소드
    // 특정 이름을 가진 회원 조회
    @Query("SELECT m FROM Member m WHERE m.name = ?1")
    List<Member> findByNameContaining(String name);

    // 특정 나이보다 많은 회원 조회
    @Query("SELECT m FROM Member m WHERE m.age > ?1")
    List<Member> findByAgeGreaterThan(int age);

    // 특정 팀의 회원조회
    @Query("SELECT m FROM Member m WHERE m.team = ?1")
    List<Member> findByTeam(Team team);

    // 나이 합계, 나이 평균, 연장자, 최연소, 인원수 정보 추출
    @Query("SELECT SUM(m.age), AVG(m.age), MAX(m.age), MIN(m.age), COUNT(m.id) FROM Member m")
    List<Object[]> aggregate();

    // 내부조인인 경우 on 절 생략
    @Query("SELECT m FROM Member m JOIN m.team t WHERE t.name = ?1")
    List<Member> findByTeam2(String teamName);

    // member와 team 정보
    @Query("SELECT m,t FROM Member m JOIN m.team t WHERE t.name = ?1")
    List<Object[]> findByTeam3(String teamName);

    // 오리지널 SQL쿼리문 사용
    @Query(value = "SELECT * FROM JPQL_MEMBER jm JOIN JPQL_TEAM jt ON JM.TEAM_TEAM_ID = jt.TEAM_ID WHERE jt.TEAM_NAME = ?1", nativeQuery = true)
    List<Object[]> findByTeam4(String teamName);

    // 외부조인 outer 생략가능(써도 무방함)
    @Query("SELECT m,t FROM Member m LEFT JOIN m.team t ON m.id = t.id WHERE t.name = ?1")
    List<Object[]> findByTeam5(String teamName);
}
