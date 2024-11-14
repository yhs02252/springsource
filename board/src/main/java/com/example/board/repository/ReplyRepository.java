package com.example.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import com.example.board.entity.Reply;

public interface ReplyRepository extends JpaRepository<Reply, Long>, QuerydslPredicateExecutor<Reply> {

}
