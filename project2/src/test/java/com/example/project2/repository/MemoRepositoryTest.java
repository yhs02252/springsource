package com.example.project2.repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.LongStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.project2.entity.Memo;

// Spring boot 테스트 목적 클래스
// 테스트 클래스는 생성자, Setter 등 생성 불가

@SpringBootTest
public class MemoRepositoryTest {

    @Autowired
    private MemoRepository memoRepository; // 현재 상태로는 객체로 지정된것이 없기에 NullPointerExceptio이 뜸

    // C(insert)
    @Test
    public void insertTest() {

        // Spring -> 자동으로 객체를 넣어주는 기능 제공
        LongStream.rangeClosed(1, 10).forEach(i -> {

            Memo memo = Memo.builder().memoText("Memo Text..." + i).build();

            System.out.println(memoRepository.save(memo)); // SQL 구문 확인가능 - DEBUG CONSOLE
        });
    }

    // R(Read)
    @Test
    public void selectOneTest() {
        Optional<Memo> result = memoRepository.findById(5L);

        Memo memo = result.get();
        System.out.println(memo);
    }

    @Test
    public void selectAllTest() {
        List<Memo> list = memoRepository.findAll();

        for (Memo memo : list) {
            System.out.println(memo);
        }
    }

    // U(Update)
    @Test
    public void updateTest() {
        Optional<Memo> result = memoRepository.findById(5L);

        result.ifPresent(memo -> { // ※ memo : 원하는 변수명 지정
            memo.setMemoText("Update Title...");
            System.out.println(memoRepository.save(memo));
        });
    }

    // D(Delete)
    @Test
    public void deleteTest() {
        // Optional<Memo> result = memoRepository.findById(10L);

        // result.ifPresent(memo -> {
        // memoRepository.delete(memo);
        // });

        memoRepository.deleteById(3L);
    }
}
