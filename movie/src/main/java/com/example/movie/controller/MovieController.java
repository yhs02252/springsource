package com.example.movie.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.movie.dto.MovieDTO;
import com.example.movie.dto.PageRequestDTO;
import com.example.movie.dto.PageResultDTO;
import com.example.movie.service.MovieService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/movie")
public class MovieController {

    private final MovieService service;

    @GetMapping("/list")
    public void getList(@ModelAttribute("requestDTO") PageRequestDTO pageRequestDTO, Model model) {
        log.info("전체 movie list 요청");

        PageResultDTO<MovieDTO, Object[]> result = service.getList(pageRequestDTO);

        model.addAttribute("result", result);
    }

    @GetMapping({ "/read", "/modify" })
    public void getRead(@RequestParam Long mno, @ModelAttribute("requestDTO") PageRequestDTO pageRequestDTO,
            Model model) {
        log.info("영화 상세 조회");

        MovieDTO movieDTO = service.get(mno);

        model.addAttribute("movieDTO", movieDTO);
    }

    @PostMapping("/remove")
    public String postRemoveMovie(Long mno, @ModelAttribute("requestDTO") PageRequestDTO pageRequestDTO,
            RedirectAttributes rttr) {
        log.info("영화 삭제 요청");

        service.delete(mno);

        rttr.addAttribute("page", pageRequestDTO.getPage());
        rttr.addAttribute("size", pageRequestDTO.getSize());
        rttr.addAttribute("type", pageRequestDTO.getType());
        rttr.addAttribute("keyword", pageRequestDTO.getKeyword());
        return "redirect:/movie/list";
    }

}
