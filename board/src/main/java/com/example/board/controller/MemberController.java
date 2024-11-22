package com.example.board.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.board.dto.MemberDTO;
import com.example.board.service.BoardUserService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final BoardUserService boardUserService;

    // @PreAuthorize("permitAll()")
    @GetMapping("/login")
    public void getLogin() {
        log.info("로그인 페이지 요청");
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/register")
    public void getRegister(MemberDTO mDto) {
        log.info("회원가입 페이지 요청");
    }

    @PreAuthorize("permitAll()")
    @PostMapping("/register")
    public String postMethodName(@Valid MemberDTO mDto, BindingResult result, RedirectAttributes rttr) {
        log.info("회원가입 요청 {}", mDto);

        if (result.hasErrors()) {
            return "/member/register";
        }

        // 서비스 작업
        String name = "";
        try {
            name = boardUserService.register(mDto);
            rttr.addFlashAttribute("name", name);
            return "redirect:/member/login";
        } catch (Exception e) {
            log.info(e.getMessage());
            rttr.addFlashAttribute("msg", e.getMessage());
            return "redirect:/member/register";
        }

    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/auth")
    @ResponseBody
    public Authentication getMethodName() {

        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        return authentication;
    }
}
