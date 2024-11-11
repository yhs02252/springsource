package com.example.project2.repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.example.project2.entity.Board;
import com.example.project2.entity.ProBoard;

@SpringBootTest
public class BoardRepositoryTest {

    @Autowired
    private BoardRepository boardRepository;

    @Test
    public void insertTest() {
        IntStream.rangeClosed(21, 300).forEach(i -> {
            ProBoard proBoard = ProBoard.builder()
                    .id(1L + i)
                    .title("제목" + i)
                    .content("내용" + i)
                    .writer("작성자" + i)
                    .build();
            boardRepository.save(proBoard);
        });
    }

    @Test
    public void selectOneTest() {
        ProBoard proBoard = boardRepository.findById(1L).get();
        System.out.println(proBoard);
    }

    @Test
    public void selectAllTest() {
        // 1번 방법
        boardRepository.findAll().forEach(board -> System.out.println(board));

        // 2번 방법
        // IntStream.rangeClosed(0, 9).forEach(i -> {
        // ProBoard proBoard = boardRepository.findAll().get(i);
        // System.out.println(proBoard);
        // });

        // IntStream.rangeClosed(0, 9).forEach(i -> {
        // List<ProBoard> proBoard = boardRepository.findAll();
        // System.out.println(proBoard);
        // });

        // IntStream.rangeClosed(0, 9).forEach(i -> {
        // Optional<ProBoard> proBoard = boardRepository.findById(1L + i);
        // System.out.println(proBoard);
        // });

        // 3번 방법 (근본)
        // List<ProBoard> boards = boardRepository.findAll();
        // for (ProBoard proBoard : boards) {
        // System.out.println(proBoard);
        // }
    }

    @Test
    public void updateTest() {
        // 1번 방법 - 각 컬럼별 선택
        ProBoard proBoard = boardRepository.findById(13L).get();
        proBoard.setTitle("추가 제목");
        proBoard.setWriter("다른작성자");
        boardRepository.save(proBoard);

        // 2번 방법 - 하나의 열 전체 컬럼 선택
        // ProBoard proBoard = ProBoard.builder()
        // .id(8L)
        // .content("아무내용")
        // .title("아무제목")
        // .writer("아무작성자")
        // .build();
        // boardRepository.save(proBoard);
    }

    @Test
    public void deleteTest() {
        // 1번 방법
        boardRepository.deleteById(17L);

        // 2번 방법
        // Optional<ProBoard> result = boardRepository.findById(11L);
        // result.ifPresent(idnum -> boardRepository.delete(idnum));

    }

    // 쿼리 메소드
    @Test
    public void testTitleList() {
        // boardRepository.findByTitle("제목6").forEach(i -> System.out.println(i));
        // boardRepository.findByTitleLike("제목").forEach(i -> System.out.println(i));
        // boardRepository.findByTitleStartingWith("제목").forEach(n ->
        // System.out.println(n));
        // boardRepository.findByWriterEndingWith("작성자").forEach(e ->
        // System.out.println(e));
        // boardRepository.findByWriterContaining("작성자").forEach(c ->
        // System.out.println(c));
        // boardRepository.findByWriterContainingOrTitleContaining("작성자",
        // "제목").forEach(k -> System.out.println(k));
        // boardRepository.findByTitleContainingAndIdGreaterThan("제목", 10L).forEach(l ->
        // System.out.println(l));
        // boardRepository.findByIdGreaterThanOrderByIdDesc(10L).forEach(o ->
        // System.out.println(o));

        // 0 : 1 page 의미, pageSize : 한 페이지에 보여질 게시물 게수
        // Pageable pageable = PageRequest.of(0, 10);

        // boardRepository.findByIdGreaterThanOrderByIdDesc(0L, pageable).forEach(s ->
        // System.out.println(s));

        // boardRepository.findByWriterList("작성자").forEach(q -> System.out.println(q));

        // boardRepository.findByTitle("제목16").forEach(b -> System.out.println(b));

        // boardRepository.findByTitleAndWriterOrderByIdDesc("제목15", "작성자15").forEach(d
        // -> System.out.println(d));
        // boardRepository.findByTitleAndWriterLike("작성자", "제목").forEach(l ->
        // System.out.println(l));
        boardRepository.findByTitleAndWriterLikeOrderByIdDesc("작성자", "제목").forEach(l -> System.out.println(l));
    }
}
