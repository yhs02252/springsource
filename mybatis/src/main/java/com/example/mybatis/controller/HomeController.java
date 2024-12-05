package com.example.mybatis.controller;

import org.springframework.stereotype.Controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.mybatis.dto.PageRequestDTO;

@Log4j2
@Controller
public class HomeController {

    @GetMapping("/")
    public String getHome(PageRequestDTO pageRequestDTO) {
        log.info("홈 화면 요청");
        return "index";
    }

}
