package com.example.project1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.project1.dto.LoginDTO;
import com.example.project1.dto.MemberDTO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Log4j2
@Controller
@RequestMapping("/member")
public class MemberController {

    @GetMapping("/login")
    public void getLogin(LoginDTO loginDTO) {
        log.info("login 페이지 요청");
    }

    /*
     * 첫번째 방법(기존)
     * 
     * @PostMapping("/login")
     * public void postLogin(HttpServletRequest request) { -> Request 에 값을 담기
     * log.info("login 요청 - 사용자 입력값");
     * 
     * String userid = request.getParameter("userid");
     * String password = request.getParameter("password");
     * 
     * log.info("userid : {}, password : {}", userid, password);
     * }
     */

    /*
     * 두번째 방법(Spring)
     * 사용자 정보를 직접 가져오기
     * 
     * @PostMapping("/login")
     * public void postLogin(String userid, String password) {
     * log.info("login 요청 - 사용자 입력값");
     * log.info("userid : {}, password : {}", userid, password);
     * }
     */

    // 세번째 방법
    @PostMapping("/login")
    public String postLogin(@Valid LoginDTO loginDTO, BindingResult result) {
        log.info("login 요청 - 사용자 입력값");
        log.info("userid : {}, password : {}", loginDTO.getUserid(), loginDTO.getPassword());

        if (result.hasErrors()) {
            return "/member/login";
        }

        return "index";
    }

    @GetMapping("/register")
    public void getRegister(MemberDTO memberDTO) {
        log.info("register 페이지 요청");
    }

    // post / return 로그인 페이지
    @PostMapping("/register")
    public String postRegister(@Valid MemberDTO memberDTO, BindingResult result) {
        log.info("register 요청");
        log.info("userid : {}, password : {}, name : {}", memberDTO.getUserid(), memberDTO.getPassword(),
                memberDTO.getName());

        if (result.hasErrors()) {
            return "/member/register";
        }

        return "/member/login";
    }

    // 컨트롤러에서 메소드 생성 방법

    // http://localhost:8080/path + get
    @GetMapping("/path1")
    public void method() {

    }

    // http://localhost:8080/path2 + post
    @PostMapping("/path2")
    public void method2() { // /path2.html
        // 1. 입력값 가져오기
        // 2. service 호출 후 결과 받기
        // 3. model.addAttribute
        // 4. 페이지 이동
    }

    @GetMapping("/path3")
    public String method3() {

        return "redirect:/login"; // http://localhost:8080/login
    }

}