package com.example.board.controller;

import org.springframework.stereotype.Controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.board.service.BoardService;

@Log4j2
@Controller
@RequiredArgsConstructor
public class HomeController {

    @GetMapping("/")
    public String getMethodName() {
        log.info("페이지 정보 찾기");

        return "redirect:/board/list"; // redirect로 값 보내기
    }

}
