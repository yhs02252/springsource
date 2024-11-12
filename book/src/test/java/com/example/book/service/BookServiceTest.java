package com.example.book.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.book.dto.BookDTO;
import com.example.book.dto.PageRequestDTO;
import com.example.book.dto.PageResultDTO;
import com.example.book.entity.Book;

import jakarta.transaction.Transactional;

@SpringBootTest
public class BookServiceTest {

    @Autowired
    private BookService bookService;

    @Test
    @Transactional
    public void testList() {

        PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
                .page(11)
                .size(10)
                .build();

        PageResultDTO<BookDTO, Book> pageResultDTO = bookService.getList(pageRequestDTO);
        pageResultDTO.getDtoList().forEach(dto -> System.out.println(dto));
        System.out.println("요청 페이지 : " + pageResultDTO.getPage());
        System.out.println("목록 개수 : " + pageResultDTO.getSize());
        System.out.println("시작 페이지 : " + pageResultDTO.getStart());
        System.out.println("마지막 페이지 : " + pageResultDTO.getEnd());
        System.out.println("pageList : " + pageResultDTO.getPageList());
        System.out.println("이전 페이지 여부 : " + pageResultDTO.isPrev());
        System.out.println("다음 페이지 여부 : " + pageResultDTO.isNext());
        System.out.println("전체 페이지 : " + pageResultDTO.getTotalPage());
    }
}
