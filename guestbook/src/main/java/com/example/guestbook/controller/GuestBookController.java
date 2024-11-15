package com.example.guestbook.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.guestbook.dto.GuestBookDTO;
import com.example.guestbook.dto.PageRequestDTO;
import com.example.guestbook.dto.PageResultDTO;
import com.example.guestbook.entity.GuestBook;
import com.example.guestbook.service.GuestBookService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;

@Log4j2
@Controller
@RequestMapping("/guestbook")
@RequiredArgsConstructor
public class GuestBookController {

    private final GuestBookService service;

    @GetMapping("/list")
    public void getList(@ModelAttribute("requestDTO") PageRequestDTO pageRequestDTO, Model model) {
        // @ModelAttribute("requestDTO") PageRequestDTO pageRequestDTO

        // ==> "requestDTO" 라는 이름에 PageRequestDTO 요소값 parameter(파라미터)를 담겠다

        log.info("list 요청 {}", pageRequestDTO);
        // List<GuestBookDTO> list = service.getList(); // 기존방식
        PageResultDTO<GuestBookDTO, GuestBook> result = service.list(pageRequestDTO);

        // model.addAttribute("list", list); // 기존 방식 요소 담기
        model.addAttribute("result", result);
        System.out.println("페이지 정보 요청 결과 : " + pageRequestDTO);
        System.out.println("전체 페이지 수 : " + result.getTotalPage());
    }

    @GetMapping({ "/read", "/modify" })
    public void getRead(@RequestParam Long gno, @ModelAttribute("requestDTO") PageRequestDTO requestDTO, Model model) {
        log.info("상세 조회 {}", gno);

        GuestBookDTO dto = service.read(gno);

        model.addAttribute("dto", dto);
    }

    @PostMapping("/modify")
    public String postModify(GuestBookDTO dto, PageRequestDTO requestDTO, RedirectAttributes rttr) {
        log.info("정보 수정 요청");
        log.info("페이지 정보 확인 : {} - {}", dto, requestDTO);

        Long gno = service.update(dto);

        rttr.addAttribute("gno", gno);
        rttr.addAttribute("page", requestDTO.getPage());
        rttr.addAttribute("size", requestDTO.getSize());
        rttr.addAttribute("type", requestDTO.getType());
        rttr.addAttribute("keyword", requestDTO.getKeyword());
        return "redirect:read";
    }

    @PostMapping("/remove")
    public String postMethodName(@RequestParam Long gno, PageRequestDTO requestDTO, RedirectAttributes rttr) {
        log.info("도서 삭제 요청 {}", gno);
        log.info("pageRequestDTO {}", requestDTO);

        service.delete(gno);

        rttr.addAttribute("page", requestDTO.getPage());
        rttr.addAttribute("size", requestDTO.getSize());
        rttr.addAttribute("type", requestDTO.getType());
        rttr.addAttribute("keyword", requestDTO.getKeyword());

        return "redirect:list";
    }

    @GetMapping("/create")
    public void getRegister(GuestBookDTO dto, @ModelAttribute("requestDTO") PageRequestDTO requestDTO) {
        log.info("입력 폼 요정 : {}", dto);
        System.out.println("페이지 정보 검사" + requestDTO);

    }

    @PostMapping("/create")
    public String postRegister(@Valid GuestBookDTO guestBookDTO, BindingResult result,
            @ModelAttribute("requestDTO") PageRequestDTO requestDTO,
            RedirectAttributes rttr) {

        if (result.hasErrors()) {
            return "/guestbook/create";
        }

        Long gno = service.register(guestBookDTO);

        rttr.addFlashAttribute("msg", gno + "작성되었습니다");
        rttr.addAttribute("page", requestDTO.getPage());
        rttr.addAttribute("size", requestDTO.getSize());
        rttr.addAttribute("type", requestDTO.getType());
        rttr.addAttribute("keyword", requestDTO.getKeyword());
        return "redirect:list";
    }

}
