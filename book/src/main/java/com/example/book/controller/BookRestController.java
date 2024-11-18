package com.example.book.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.book.dto.BookDTO;
import com.example.book.dto.CategoryDTO;
import com.example.book.dto.PageRequestDTO;
import com.example.book.dto.PageResultDTO;
import com.example.book.dto.PublisherDTO;
import com.example.book.entity.Book;
import com.example.book.service.BookService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@Log4j2
@RequestMapping("/rest")
@RequiredArgsConstructor
public class BookRestController {

    private final BookService service;

    @GetMapping("/list")
    public ResponseEntity<PageResultDTO<BookDTO, Book>> getList(PageRequestDTO requestDTO) {
        log.info("도서 전체 목록 요청");

        PageResultDTO<BookDTO, Book> result = service.getList(requestDTO);
        return new ResponseEntity<PageResultDTO<BookDTO, Book>>(result, HttpStatus.OK);

    }

    @PostMapping("/create")
    public ResponseEntity<String> postCreateBook(BookDTO dto) {
        log.info("도서 입력 요청 {}", dto);

        // 서비스 insert 호출
        Long id = service.create(dto);

        return new ResponseEntity<String>("SUCCESS", HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> putUpdate(@PathVariable Long id, BookDTO dto) {
        log.info("도서정보 수정 요청 : {}", dto);

        dto.setId(id);
        id = service.updatePrice(dto);

        return new ResponseEntity<String>("SUCCESS", HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> postRemoveBook(@PathVariable Long id) {
        log.info("도서 삭제 요청 {}", id);

        service.delete(id);

        return new ResponseEntity<String>("SUCCESS", HttpStatus.OK);
    }
}
