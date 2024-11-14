package com.example.guestbook.controller;

import org.springframework.stereotype.Controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;

@Log4j2
@Controller
public class HomeController {

    @GetMapping("/")
    public String getHome() {
        log.info("홈 화면 요청");

        return "redirect:/guestbook/list";
    }

}
