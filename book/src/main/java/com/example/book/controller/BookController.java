package com.example.book.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.book.dto.BookDTO;
import com.example.book.dto.CategoryDTO;
import com.example.book.dto.PublisherDTO;
import com.example.book.service.BookService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Log4j2
@Controller
@RequestMapping("/book")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @GetMapping("/bookList")
    public void getBookList(Model model) {
        log.info("도서 리스트 목록 요청");
        List<BookDTO> list = bookService.getList();
        model.addAttribute("list", list);
    }

    @GetMapping(path = { "/read", "/modify" })
    public void getBookRead(@RequestParam Long id, Model model) {
        log.info("도서 상세 페이지 요청 {}", id);
        // 상세내용

        BookDTO dto = bookService.read(id);

        model.addAttribute("dto", dto);
    }

    @PostMapping("/modify")
    public String postBookModify(BookDTO dto, RedirectAttributes rttr) {
        log.info("도서정보 수정 요청");

        Long id = bookService.updatePrice(dto);

        rttr.addAttribute("id", id);

        return "redirect:read";
    }

    @PostMapping("/remove")
    public String postRemoveBook(@RequestParam Long id) {
        log.info("도서 삭제 요청 {}", id);

        bookService.delete(id);
        return "redirect:bookList";
    }

    @GetMapping("/create")
    public void getCreateBook(@ModelAttribute("dto") BookDTO dto, Model model) {
        log.info("도서 입력 폼 요청 {}", dto);

        List<CategoryDTO> categories = bookService.getCateList();
        List<PublisherDTO> publishers = bookService.getPublList();

        model.addAttribute("cDtos", categories);
        model.addAttribute("pDtos", publishers);
    }

    @PostMapping("/create")
    public String postCreateBook(@Valid @ModelAttribute("dto") BookDTO dto, BindingResult result, Model model,
            RedirectAttributes rttr) {
        log.info("도서 입력 요청 {}", dto);

        List<CategoryDTO> categories = bookService.getCateList();
        List<PublisherDTO> publishers = bookService.getPublList();

        model.addAttribute("cDtos", categories);
        model.addAttribute("pDtos", publishers);

        if (result.hasErrors()) {
            return "/book/create";
        }

        // 서비스 insert 호출
        Long id = bookService.create(dto);
        rttr.addFlashAttribute("id", id + "번 도서가 등록되었습니다");

        return "redirect:bookList";
    }

}
