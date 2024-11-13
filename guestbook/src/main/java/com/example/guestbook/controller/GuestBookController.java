package com.example.guestbook.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.guestbook.dto.GuestBookDTO;
import com.example.guestbook.dto.PageRequestDTO;
import com.example.guestbook.dto.PageResultDTO;
import com.example.guestbook.entity.GuestBook;
import com.example.guestbook.service.GuestBookService;

@Log4j2
@Controller
@RequestMapping("/guestbook")
@RequiredArgsConstructor
public class GuestBookController {

    private final GuestBookService service;

    @GetMapping("/list")
    public void getList(PageRequestDTO pageRequestDTO, Model model) {
        log.info("list 요청 {}", pageRequestDTO);
        // List<GuestBookDTO> list = service.getList(); // 기존방식
        PageResultDTO<GuestBookDTO, GuestBook> result = service.list(pageRequestDTO);

        // model.addAttribute("list", list); // 기존 방식 요소 담기
        model.addAttribute("result", result);
        System.out.println("페이지 정보 요청 결과 : " + pageRequestDTO);
    }

}
