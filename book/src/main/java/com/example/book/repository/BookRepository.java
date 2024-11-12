package com.example.book.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import com.example.book.entity.Book;
import com.example.book.entity.QBook;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;

public interface BookRepository extends JpaRepository<Book, Long>, QuerydslPredicateExecutor<Book> {

    default Predicate makePredicate(String type, String keyword) {

        BooleanBuilder builder = new BooleanBuilder();
        QBook qBook = QBook.book;

        // id > 0 : range scan
        // 페이지 나누기 시 id값이 없다면 id가 0보다 큰 글이 포함되도록 지정하기 위한 쿼리문 생성
        builder.and(qBook.id.gt(0L));

        if (type == null)
            return builder;
        if (type.equals("c")) { // 카테고리
            builder.and(qBook.category.name.contains(keyword));
        } else if (type.equals("t")) { // 제목
            builder.and(qBook.title.contains(keyword));
        } else if (type.equals("w")) { // 저자
            builder.and(qBook.writer.contains(keyword));
        }

        return builder;
    }
}
