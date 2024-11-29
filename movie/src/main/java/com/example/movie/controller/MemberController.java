package com.example.movie.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.movie.dto.PageRequestDTO;

@Log4j2
@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    @GetMapping("/login")
    public void getLogin(@ModelAttribute("requestDTO") PageRequestDTO pageRequestDTO) {
        log.info("로그인 페이지 요청");
    }

    @GetMapping("/forgotpassword")
    public void getpass(@ModelAttribute("requestDTO") PageRequestDTO pageRequestDTO) {
        log.info("비번 찾기 페이지 요청");
    }

    @GetMapping("/createaccount")
    public void getCreateid(@ModelAttribute("requestDTO") PageRequestDTO pageRequestDTO) {
        log.info("계정 만들기 요청");
    }

    @GetMapping("/url404")
    public void get404(@ModelAttribute("requestDTO") PageRequestDTO pageRequestDTO) {
        log.info("404 페이지 요청");
    }

}
