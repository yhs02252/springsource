package com.example.club.controller;

import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.club.dto.ClubMemberDTO;
import com.example.club.service.ClubUserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@Log4j2
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final ClubUserService clubUserService; // <= final 필수 : 안쓰면 값이 안 들어감
    private ClubUserService clubUserService2; // @RequiredArgsConstructor 작동안함

    @GetMapping("/login")
    public void getLogin() {
        log.info("로그인 폼 요청");
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/member")
    public void getMember() {
        log.info("Member 페이지 요청");
    }

    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    @GetMapping("/admin")
    public void getAdmin() {
        log.info("admin 페이지 요청");
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/auth")
    @ResponseBody
    public Authentication getMethodName() {

        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        return authentication;
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/register")
    public void getRegister(ClubMemberDTO dto) {
        log.info("회원 가입 폼 요청");
    }

    @PreAuthorize("permitAll()")
    @PostMapping("/register")
    public String postRegister(@Valid ClubMemberDTO dto, BindingResult result, RedirectAttributes rttr) {
        log.info("회원 가입 요청 : {}", dto);

        if (result.hasErrors()) {
            return "/users/register";
        }

        System.out.println("service 값 확인 : " + clubUserService);
        System.out.println("service2 값 확인 : " + clubUserService2);
        System.out.println("service.register 값 확인 : " + clubUserService);
        System.out.println("service2.register 값 확인 : " + clubUserService2);
        String email = clubUserService.register(dto);
        rttr.addFlashAttribute("email", email);

        return "redirect:/users/login";
    }

}
