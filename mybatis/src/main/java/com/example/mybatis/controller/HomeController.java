package com.example.mybatis.controller;

import org.springframework.stereotype.Controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.mybatis.dto.PageRequestDTO;

@Log4j2
@Controller
public class HomeController {

    @GetMapping("/")
    public String getHome(PageRequestDTO pageRequestDTO, RedirectAttributes rttr) {
        log.info("홈 화면 요청");

        rttr.addAttribute("page", pageRequestDTO.getPage());
        rttr.addAttribute("size", pageRequestDTO.getSize());
        rttr.addAttribute("type", pageRequestDTO.getType());
        rttr.addAttribute("keyword", pageRequestDTO.getKeyword());
        return "index";
    }

}
