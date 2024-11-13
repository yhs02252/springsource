package com.example.guestbook.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import com.example.guestbook.entity.GuestBook;
import com.example.guestbook.entity.QGuestBook;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;

public interface GuestBookRepository extends JpaRepository<GuestBook, Long>, QuerydslPredicateExecutor<GuestBook> {
    default Predicate makePredicate(String type, String keyword) {

        BooleanBuilder builder = new BooleanBuilder();
        QGuestBook qGuestBook = QGuestBook.guestBook;

        builder.and(qGuestBook.gno.gt(0L));

        if (type == null) {
            return builder;
        }

        BooleanBuilder conditionBuilder = new BooleanBuilder();
        // if (type.equals("c")) {
        if (type.contains("c")) { // 내용
            conditionBuilder.or(qGuestBook.content.contains(keyword));
        }
        if (type.contains("t")) { // 제목
            conditionBuilder.or(qGuestBook.title.contains(keyword));
        }
        if (type.contains("w")) { // 작성자
            conditionBuilder.or(qGuestBook.writer.contains(keyword));
        }
        // gno > 0 and (content like %keyword% or title like %keyword% or writer like
        // %keyword%)
        builder.and(conditionBuilder);

        return builder;
    }
}
