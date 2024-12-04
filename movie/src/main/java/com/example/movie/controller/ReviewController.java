package com.example.movie.controller;

import org.springframework.web.bind.annotation.RequestMapping;

import com.example.movie.dto.ReviewDTO;
import com.example.movie.service.ReviewService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;

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

    @PreAuthorize("authentication.name == #memberEmail")
    @DeleteMapping("/{mno}/{reviewNo}")
    public ResponseEntity<Long> deleteReview(@PathVariable Long reviewNo, String memberEmail) {
        log.info("삭제될 rno 확인 : {}", reviewNo);

        reviewService.removeReview(reviewNo);

        return new ResponseEntity<Long>(reviewNo, HttpStatus.OK);
    }

    @GetMapping("/{mno}/{reviewNo}")
    public ReviewDTO geOneReview(@PathVariable Long reviewNo) {
        log.info("가져올 rno 확인 : {}", reviewNo);

        ReviewDTO reviewDTO = reviewService.getReview(reviewNo);

        return reviewDTO;
    }

    @PreAuthorize("authentication.name == #reviewDTO.memberEmail")
    @PutMapping("/{mno}/{reviewNo}")
    public Long putModify(@PathVariable Long reviewNo, @RequestBody ReviewDTO reviewDTO) {

        reviewDTO.setReviewNo(reviewNo);
        reviewNo = reviewService.modifyReview(reviewDTO);

        return reviewNo;
    }

    @PostMapping("/{mno}/new")
    public ResponseEntity<Long> postMethodName(@RequestBody ReviewDTO reviewDTO) {
        log.info("새 리뷰 정보 : {}", reviewDTO);

        Long reviewNo = reviewService.addReview(reviewDTO);

        return new ResponseEntity<Long>(reviewNo, HttpStatus.OK);
    }

}
