package com.example.project2.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.project2.entity.Memo;

public interface MemoRepository extends JpaRepository<Memo, Long> {
    // save(Entity) : insert, update
    // findByid(pk값) : select 한 열 조회
    // findAll() : select 전체조회
    // deleteById(pk값) : delete 한 열 삭제
    // delete(Entity) : delete 지정한 항목 삭제
}
