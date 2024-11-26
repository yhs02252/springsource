package com.example.movie.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.movie.dto.MovieDTO;

@SpringBootTest
public class MovieServiceTest {

    @Autowired
    private MovieService movieService;

    @Test
    public void testGet() {
        MovieDTO movieDTO = movieService.get(1L);
        System.out.println(movieDTO);
    }
}
