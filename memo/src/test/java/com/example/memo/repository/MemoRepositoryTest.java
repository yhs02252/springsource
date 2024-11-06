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
        LongStream.rangeClosed(1, 10).forEach(i -> {
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
}
