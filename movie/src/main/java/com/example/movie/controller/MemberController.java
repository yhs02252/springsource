package com.example.movie.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.movie.dto.AuthMemberDTO;
import com.example.movie.dto.MemberDTO;
import com.example.movie.dto.PageRequestDTO;
import com.example.movie.dto.PasswordDTO;
import com.example.movie.service.MemberService;

import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Log4j2
@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;

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

    @GetMapping("/profile")
    public void getProfile(@ModelAttribute("requestDTO") PageRequestDTO pageRequestDTO) {
        log.info("profile 요청");
    }

    @GetMapping("/edit")
    public void getEdir(@ModelAttribute("requestDTO") PageRequestDTO pageRequestDTO) {
        log.info("edit 요청");
    }

    // 닉네임 수정
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/edit/nickname")
    public String postNicknameEdit(MemberDTO memberDTO) {
        log.info("nickname 수정 요청 {}", memberDTO);

        // email 가져오기
        Authentication authentication = getAuthentication();

        // MemberDTO 에 들어있는 값 접근시
        AuthMemberDTO authMemberDTO = (AuthMemberDTO) authentication.getPrincipal();
        memberDTO.setEmail(authMemberDTO.getUsername());

        memberService.nickNameUpdate(memberDTO);

        // securityContext 에 보관된 값 업데이트
        authMemberDTO.getMemberDTO().setNickName(memberDTO.getNickName());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return "redirect:/member/profile";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/edit/password")
    public String postPasswordEdit(PasswordDTO passwordDTO, HttpSession session, RedirectAttributes rttr) {
        log.info("password 수정 요청 {}", passwordDTO);

        // 서비스 호출
        try {
            memberService.passwordUpdate(passwordDTO);

        } catch (Exception e) {

            // 실패시 exception 날리고 edit 로 돌아가기
            e.printStackTrace();
            rttr.addFlashAttribute("error", e.getMessage());
            return "redirect:/member/edit";
        }

        // 성공시 세션 해제 후 / Login 이동
        session.invalidate();

        return "redirect:/member/login";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/auth")
    @ResponseBody
    public Authentication getAuthentication() {

        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        return authentication;
    }
}
