package com.example.memo.controller;

import org.springframework.stereotype.Controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Log4j2
@Controller
public class Homecontroller {

    @GetMapping("/")
    public String getHome() {
        log.info("메모 페이지 홈 화면 요청");
        return "index";
    }

}
