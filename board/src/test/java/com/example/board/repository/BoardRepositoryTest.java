package com.example.board.repository;

import java.util.stream.IntStream;
import java.util.stream.LongStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.board.entity.Board;
import com.example.board.entity.Member;
import com.example.board.entity.Reply;

import jakarta.transaction.Transactional;

@SpringBootTest
public class BoardRepositoryTest {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ReplyRepository replyRepository;

    @Test
    public void memberInsertTest() {
        IntStream.rangeClosed(1, 30).forEach(i -> {
            int num = (int) (Math.random() * 30) * 10;

            Member member = Member.builder()
                    .email("email" + i + "@naver.com")
                    .name("user" + i)
                    .password("pass" + num + i)
                    .build();

            memberRepository.save(member);
        });

        LongStream.rangeClosed(1, 100).forEach(i -> {

            int num = (int) (Math.random() * 30) + 1;
            int num2 = (int) (Math.random() * 100) * 10;
            Member member = memberRepository.findById("email" + num + "@naver.com").get();

            Board board = Board.builder()
                    .content("content" + num2)
                    .title("title" + i)
                    .writer(member)
                    .build();
            boardRepository.save(board);
        });

        LongStream.rangeClosed(1, 100).forEach(i -> {
            int num = (int) (Math.random() * 30) + 1;
            int num2 = (int) (Math.random() * 100) + 1;
            Board board = boardRepository.findById(i).get();

            Reply reply = Reply.builder()
                    .replyer("email" + num + "@naver.com")
                    .text("randomtext" + num2)
                    .board(board)
                    .build();
            replyRepository.save(reply);
        });
    }

}
