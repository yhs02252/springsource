package com.example.mybatis.controller;

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

import com.example.mybatis.dto.BookDTO;
import com.example.mybatis.dto.CategoryDTO;
import com.example.mybatis.dto.PageRequestDTO;
import com.example.mybatis.dto.PageResultDTO;
import com.example.mybatis.dto.PublisherDTO;
import com.example.mybatis.service.BookService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Log4j2
@Controller
@RequestMapping("/book")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @GetMapping("/bookList")
    public void getBookList(PageRequestDTO pageRequestDTO, Model model) {
        log.info("도서 리스트 목록 요청");

        List<BookDTO> result = bookService.getList(pageRequestDTO);
        int total = bookService.getTotalCnt(pageRequestDTO);

        log.info("list : {}", result);
        log.info("total : {}", total);

        model.addAttribute("result", new PageResultDTO<>(pageRequestDTO, total, result));
    }

    @GetMapping(path = { "/read", "/modify" })
    public void getBookRead(@RequestParam Long id, PageRequestDTO pageRequestDTO, Model model) {
        log.info("도서 상세 페이지 요청 {}", id);
        // 상세내용

        BookDTO dto = bookService.read(id);

        model.addAttribute("dto", dto);
    }

    @PostMapping("/modify")
    public String postBookModify(BookDTO dto, PageRequestDTO pageRequestDTO, RedirectAttributes rttr) {
        log.info("도서정보 수정 요청");
        log.info("dto 정보 : {}", dto);
        log.info("pageRequestDTO 정보 : {}", pageRequestDTO);

        if (bookService.updatePrice(dto)) {
            // 상세조회로 이동
            rttr.addAttribute("id", dto.getId());
            rttr.addAttribute("page", pageRequestDTO.getPage());
            rttr.addAttribute("size", pageRequestDTO.getSize());
            rttr.addAttribute("type", pageRequestDTO.getType());
            rttr.addAttribute("keyword", pageRequestDTO.getKeyword());

            return "redirect:read";
        } else {

            return "/book/modify";
        }
    }

    @PostMapping("/remove")
    public String postRemoveBook(@RequestParam Long id, PageRequestDTO pageRequestDTO, RedirectAttributes rttr) {
        log.info("도서 삭제 요청 {}", id);
        log.info("pageRequestDTO {}", pageRequestDTO);

        bookService.delete(id);

        rttr.addAttribute("page", pageRequestDTO.getPage());
        rttr.addAttribute("size", pageRequestDTO.getSize());
        rttr.addAttribute("type", pageRequestDTO.getType());
        rttr.addAttribute("keyword", pageRequestDTO.getKeyword());
        return "redirect:bookList";
    }

    @GetMapping("/create")
    public void getCreateBook(@ModelAttribute("dto") BookDTO dto, PageRequestDTO pageRequestDTO, Model model) {
        log.info("도서 입력 폼 요청 {}", dto);

        List<CategoryDTO> categories = bookService.getCateList();
        List<PublisherDTO> publishers = bookService.getPublList();

        model.addAttribute("cDtos", categories);
        model.addAttribute("pDtos", publishers);
    }

    @PostMapping("/create")
    public String postCreateBook(@Valid @ModelAttribute("dto") BookDTO dto, BindingResult result,
            PageRequestDTO pageRequestDTO, Model model,
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
        bookService.create(dto);

        rttr.addFlashAttribute("id", "도서가 등록되었습니다");

        rttr.addAttribute("page", 1);
        rttr.addAttribute("size", pageRequestDTO.getSize());
        rttr.addAttribute("type", pageRequestDTO.getType());
        rttr.addAttribute("keyword", pageRequestDTO.getKeyword());

        return "redirect:bookList";
    }

}
