package com.example.project1.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.LongStream;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.project1.dto.LoginDTO;
import com.example.project1.dto.SampleDTO;

@Log4j2
@Controller
public class HomeController {

    @GetMapping("/")
    public String home(Model model) {
        log.info("index 요청");

        model.addAttribute("name", "hong");

        LoginDTO loginDTO = new LoginDTO("hong465", "12345");
        model.addAttribute("login", loginDTO);

        // SampleDTO sampleDTO = new SampleDTO();
        // sampleDTO.setId(1L);
        // sampleDTO.setFirst("Adam");
        // sampleDTO.setLast("Savage");
        // sampleDTO.setRegDateTime(LocalDateTime.now());

        SampleDTO sampleDTO = SampleDTO.builder()
                .id(1L)
                .first("Adam")
                .last("Savage")
                .regDateTime(LocalDateTime.now())
                .build();

        model.addAttribute("sampleDTO", sampleDTO);

        List<SampleDTO> list = new ArrayList<>();
        LongStream.rangeClosed(1, 10).forEach(i -> {
            SampleDTO dto = SampleDTO.builder()
                    .id(i)
                    .first("first" + i)
                    .last("last" + i)
                    .regDateTime(LocalDateTime.now())
                    .build();
            list.add(dto);
        });
        model.addAttribute("list", list);

        return "index";
    }

}
