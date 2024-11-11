package com.example.memo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.memo.entity.Memo;

public interface MemoRepository extends JpaRepository<Memo, Long> {
    // where mno < 5
    List<Memo> findByMnoLessThan(Long mno);

    // where mno < 10 order by mno desc
    List<Memo> findByMnoLessThanOrderByMnoDesc(Long mno);

    // where mno >= 50 and mno <= 100 order by mno desc
    List<Memo> findByMnoGreaterThanEqualAndMnoLessThanEqualOrderByMnoDesc(Long mno, Long id);

    // where mno >= 50 and mno <= 100 order by mno desc <- 같지만 다른 방식 Between 사용
    List<Memo> findByMnoBetweenOrderByMnoDesc(Long mno, Long end);
}
