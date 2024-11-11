package com.example.memo.repository;

import java.util.List;
import java.util.stream.LongStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.memo.entity.Memo;

@SpringBootTest
public class MemoRepositoryTest {

    @Autowired
    private MemoRepository memoRepository;

    @Test
    public void testMemoInsert() {
        LongStream.rangeClosed(11, 100).forEach(i -> {
            Memo memo = Memo.builder()
                    .memoText("TestMemo" + i)
                    .build();
            memoRepository.save(memo);
        });
    }

    @Test
    public void testMemoRead() {
        // 6번 메모 가져오기
        System.out.println(memoRepository.findById(6L).get());

        // 전체메모 가져오기
        System.out.println(memoRepository.findAll());

        // 리스트구조로 가져오기
        List<Memo> memoList = memoRepository.findAll();
        memoList.forEach(memos -> System.out.println(memos));
    }

    @Test
    public void testMemoUpdate() {
        Memo memo = memoRepository.findById(7L).get();
        memo.setMemoText("ReWritedTest7");
        memoRepository.save(memo);
    }

    @Test
    public void testMemoDelete() {
        memoRepository.deleteById(8L);
    }

    // 쿼리 테스트
    @Test
    public void testQuerySelect() {
        // memoRepository.findByMnoLessThan(5L).forEach(l -> System.out.println(l));
        // memoRepository.findByMnoLessThanOrderByMnoDesc(10L).forEach(o ->
        // System.out.println(o));
        // memoRepository.findByMnoGreaterThanEqualAndMnoLessThanEqualOrderByMnoDesc(50L,
        // 100L)
        // .forEach(m -> System.out.println(m));
        memoRepository.findByMnoBetweenOrderByMnoDesc(50L, 100L).forEach(b -> System.out.println(b));
    }
}
