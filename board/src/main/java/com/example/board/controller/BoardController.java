package com.example.board.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import com.example.board.dto.BoardDTO;
import com.example.board.dto.PageRequestDTO;
import com.example.board.dto.PageResultDTO;
import com.example.board.service.BoardService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Log4j2
@Controller
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/list")
    public void getList(@ModelAttribute("requestDTO") PageRequestDTO requestDTO, Model model) {

        PageResultDTO<BoardDTO, Object[]> result = boardService.getList(requestDTO);
        model.addAttribute("result", result);
    }

    @GetMapping({ "/read", "/modify" })
    public void getReadAndModify(@RequestParam Long bno, @ModelAttribute("requestDTO") PageRequestDTO requestDTO,
            Model model) {
        log.info("정보 페이지 요청 {}", bno);

        BoardDTO dto = boardService.read(bno);

        model.addAttribute("dto", dto);

    }

    @PreAuthorize("authentication.name == #dto.writerEmail")
    @PostMapping("/modify")
    public String postMethodName(BoardDTO dto, @ModelAttribute("requestDTO") PageRequestDTO requestDTO,
            RedirectAttributes rttr) {

        Long bno = boardService.update(dto);

        rttr.addAttribute("bno", bno);
        rttr.addAttribute("page", requestDTO.getPage());
        rttr.addAttribute("size", requestDTO.getSize());
        rttr.addAttribute("type", requestDTO.getType());
        rttr.addAttribute("keyword", requestDTO.getKeyword());
        return "redirect:read";
    }

    @PreAuthorize("authentication.name == #writerEmail")
    @PostMapping("/remove")
    public String postMethodName(Long bno, String writerEmail, @ModelAttribute("requestDTO") PageRequestDTO requestDTO,
            RedirectAttributes rttr) {
        log.info("bno 확인 : {}", bno);
        log.info("requestDTO 확인 : {}", requestDTO);

        boardService.remove(bno);

        rttr.addAttribute("page", requestDTO.getPage());
        rttr.addAttribute("size", requestDTO.getSize());
        rttr.addAttribute("type", requestDTO.getType());
        rttr.addAttribute("keyword", requestDTO.getKeyword());

        return "redirect:/board/list";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/create")
    public void getMethodName(@ModelAttribute("dto") BoardDTO dto,
            @ModelAttribute("requestDTO") PageRequestDTO requestDTO) {
        log.info("register page 요청");

    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create")
    public String postMethodName(@Valid @ModelAttribute("dto") BoardDTO dto, BindingResult result,
            @ModelAttribute("requestDTO") PageRequestDTO requestDTO,
            RedirectAttributes rttr) {
        log.info("값 확인 {}", dto);
        log.info("페이지 요소 확인 {}", requestDTO);

        if (result.hasErrors()) {
            return "/board/create";
        }

        Long bno = boardService.register(dto);

        rttr.addAttribute("bno", bno);
        rttr.addAttribute("page", requestDTO.getPage());
        rttr.addAttribute("size", requestDTO.getSize());
        rttr.addAttribute("type", requestDTO.getType());
        rttr.addAttribute("keyword", requestDTO.getKeyword());

        return "redirect:list";
    }

}
