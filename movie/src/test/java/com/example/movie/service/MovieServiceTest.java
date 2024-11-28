package com.example.movie.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.movie.dto.MovieDTO;
import com.example.movie.dto.PageRequestDTO;
import com.example.movie.dto.PageResultDTO;

@SpringBootTest
public class MovieServiceTest {

    @Autowired
    private MovieService movieService;

    @Test
    public void testGet() {
        MovieDTO movieDTO = movieService.get(52L);
        System.out.println(movieDTO);
    }

    @Test
    public void testKeywordList() {
        PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
                .page(1)
                .size(10)
                .type("t")
                .keyword("오펜")
                .build();
        PageResultDTO<MovieDTO, Object[]> result = movieService.getList(pageRequestDTO);
        System.out.println(result.getDtoList());
    }
}
