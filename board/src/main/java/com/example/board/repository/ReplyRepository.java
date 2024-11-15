package com.example.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import com.example.board.entity.Reply;

public interface ReplyRepository extends JpaRepository<Reply, Long>, QuerydslPredicateExecutor<Reply> {

    // reply 테이블의 board_bno 를 이용해 reply 삭제하는 쿼리 메소드 생성
    @Modifying
    @Query("DELETE FROM Reply r WHERE r.board.bno = :bno")
    void deleteByBno(Long bno);
}
