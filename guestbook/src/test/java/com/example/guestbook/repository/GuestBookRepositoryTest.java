package com.example.guestbook.repository;

import java.util.stream.LongStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.example.guestbook.entity.GuestBook;
import com.example.guestbook.entity.QGuestBook;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;

@SpringBootTest
public class GuestBookRepositoryTest {

    @Autowired
    private GuestBookRepository guestBookRepository;

    @Test
    public void insertTest() {

        LongStream.rangeClosed(1, 300).forEach(i -> {
            long num = (int) (Math.random() * 100) + 1;

            GuestBook guestBook = GuestBook.builder()
                    .title("제목" + i)
                    .writer(i + "번 인물")
                    .content("내용 " + num + "번 숫자")
                    .build();
            guestBookRepository.save(guestBook);

        });
    }

    @Test
    public void updateTest() {

        GuestBook guestBook = guestBookRepository.findById(300L).get();
        guestBook.setTitle("수정된 제목");
        guestBookRepository.save(guestBook);
    }

    // R
    @Test
    public void readTest() {
        // 제목에 1이라는 글자가 들어있는 글 조회
        QGuestBook qGuestBook = QGuestBook.guestBook;

        // pageable 에 넣을 정보
        Pageable pageable = PageRequest.of(0, 10, Sort.by("gno").descending());

        // predicate 에 담을 정보
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(qGuestBook.title.contains("1"));

        // findAll <= QuerydslPredicateExecutor 로 가져나옴
        // predicate(BooleanBuilder 사용), pageable(페이지 정보) <- 들어가게끔
        Page<GuestBook> result = guestBookRepository.findAll(builder, pageable);
        result.stream().forEach(i -> System.out.println(i));
        System.out.println("전체 페이지 수" + result.getTotalPages());

    }

    @Test
    public void readTest2() {
        // 제목에 or 내용에 1이라는 글자가 들어있고, gno > 0 글 조회
        QGuestBook qGuestBook = QGuestBook.guestBook;

        // pageable 에 넣을 정보
        Pageable pageable = PageRequest.of(0, 10, Sort.by("gno").descending());

        // predicate 에 담을 정보
        String keyword = "1";

        BooleanBuilder builder = new BooleanBuilder();
        BooleanExpression expressionTitle = qGuestBook.title.contains(keyword);
        BooleanExpression expressionContent = qGuestBook.content.contains(keyword);

        builder.and(expressionTitle.or((expressionContent)));
        builder.and(qGuestBook.gno.gt(0));

        // findAll <= QuerydslPredicateExecutor 로 가져나옴
        // predicate(BooleanBuilder 사용), pageable(페이지 정보) <- 들어가게끔
        Page<GuestBook> result = guestBookRepository.findAll(builder, pageable);
        result.stream().forEach(i -> System.out.println(i));
        System.out.println("전체 페이지 수" + result.getTotalPages());

    }

    @Test
    public void readTest3() {

        Pageable pageable = PageRequest.of(3, 10, Sort.by("gno").descending());

        Page<GuestBook> result = guestBookRepository.findAll(guestBookRepository.makePredicate("tc", "1"), pageable);

        result.stream().forEach(guestBook -> System.out.println(guestBook));
    }

    @Test
    public void deleteTest() {
        guestBookRepository.deleteById(250L);
    }
}
