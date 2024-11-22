package com.example.board.repository;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

import javax.swing.border.Border;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Commit;

import com.example.board.entity.Board;
import com.example.board.entity.Member;
import com.example.board.entity.MemberRole;
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

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void memberInsertTest() {
        IntStream.rangeClosed(1, 30).forEach(i -> {
            int num = (int) (Math.random() * 30) * 10;

            Member member = Member.builder()
                    .email("email" + i + "@naver.com")
                    .name("user" + i)
                    .password(passwordEncoder.encode("1111"))
                    .role(MemberRole.MEMBER)
                    .build();

            memberRepository.save(member);
        });

    }

    @Test
    public void boardInsertTest() {
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

    }

    @Test
    public void replyInsertTest() {
        LongStream.rangeClosed(1, 100).forEach(i -> {
            int num = (int) (Math.random() * 30) + 1;
            long num2 = (long) (Math.random() * 100) + 121;
            Board board = boardRepository.findById(num2).get();
            Member member = memberRepository.findById("email" + num + "@naver.com").get();

            Reply reply = Reply.builder()
                    .replyer(member)
                    .text("randomtext" + num2)
                    .board(board)
                    .build();
            replyRepository.save(reply);
        });

    }

    @Test
    @Transactional
    public void boardReadTest() {
        LongStream.rangeClosed(1, 100).forEach(i -> {
            System.out.println(boardRepository.findById(i).get());

        });
        System.out.println(boardRepository.findById(100L).get().getWriter());
    }

    @Test
    @Transactional
    public void replyReadTest() {
        LongStream.rangeClosed(1, 100).forEach(i -> {
            System.out.println(replyRepository.findById(i).get());

        });
        System.out.println(replyRepository.findById(100L).get().getBoard());
    }

    @Test
    @Transactional
    public void readBoardTest() {
        Board board = boardRepository.findById(100L).get();
        System.out.println(board.getReplies());
    }

    @Test
    @Transactional
    public void readInfoTest() {
        Board board = boardRepository.findById(100L).get();

        System.out.println(board.getBno());
        System.out.println(board.getTitle());
        System.out.println(board.getWriter());
        System.out.println(board.getCreatedDateTime());
    }

    @Test
    public void readJoinQeuryTest() {
        List<Object[]> list = boardRepository.list();

        for (Object[] objects : list) {
            System.out.println(Arrays.toString(objects));

            // Board board = (Board) objects[0];
            // Member member = (Member) objects[1];
            // Long replycnt = (Long) objects[2];
        }
    }

    @Test
    // @Transactional
    public void readPageableListTest() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("bno").descending());

        Page<Object[]> result = boardRepository.list("tc", "content", pageable);

        for (Object[] objects : result) {
            System.out.println(Arrays.toString(objects));
        }
    }

    @Test
    public void joinRowTest() {

        Object[] objects = boardRepository.getBoardByBno(100L);

        System.out.println(Arrays.toString(objects));
    }

    @Test
    @Commit
    @Transactional
    public void replyDeleteTest() {
        replyRepository.deleteByBno(1L);
        boardRepository.deleteById(1L);
    }

    @Test
    public void replyDeleteTest2() {
        // 부모 제거시 자식(Reply) 제거
        boardRepository.deleteById(2L);
    }

    @Test
    public void replyListTest() {
        Board board = Board.builder().bno(10L).build();
        List<Reply> list = replyRepository.findByBoardOrderByRno(board);

        list.forEach(b -> System.out.println(b));
    }

    @Test
    public void testReplyUpdate() {
        // 댓글 수정
        Reply reply = replyRepository.findById(10L).get();
        System.out.println(reply);
        // 내용 수정
        reply.setText("내용 수정2");
        System.out.println(replyRepository.save(reply));
    }
}
