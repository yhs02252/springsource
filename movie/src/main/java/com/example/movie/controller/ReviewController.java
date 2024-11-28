package com.example.movie.controller;

import org.springframework.web.bind.annotation.RequestMapping;

import com.example.movie.dto.ReviewDTO;
import com.example.movie.service.ReviewService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping("/{mno}/all")
    public List<ReviewDTO> getReviewList(@PathVariable Long mno) {
        log.info("리뷰 리스트 요청 {}", mno);

        List<ReviewDTO> reviews = reviewService.getReviews(mno);

        return reviews;
    }

}
