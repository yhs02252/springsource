package com.example.movie.controller;

import org.springframework.stereotype.Controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.movie.dto.PageRequestDTO;

@Controller
@Log4j2
public class HomeController {

    @GetMapping("/")
    public String getHome() {
        return "redirect:/movie/list";
    }

    // 403 에러시
    @GetMapping("/access-denied")
    public String getError403(@ModelAttribute("requestDTO") PageRequestDTO pageRequestDTO) {
        return "/except/denied";
    }

    // 404 에러시
    @GetMapping("/error")
    public String getError404(@ModelAttribute("requestDTO") PageRequestDTO pageRequestDTO) {
        // 컨트롤러에 없는 경로 요청시
        return "/except/url404";
    }

}
