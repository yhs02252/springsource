package com.example.movie.service;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.movie.dto.ReviewDTO;
import com.example.movie.entity.Movie;
import com.example.movie.entity.Review;
import com.example.movie.repository.MemberRepository;
import com.example.movie.repository.MovieRepository;
import com.example.movie.repository.ReviewRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RequiredArgsConstructor
@Service
public class ReviewServiceImpl implements ReviewService {

    private final MovieRepository movieRepository;
    private final MemberRepository memberRepository;
    private final ReviewRepository reviewRepository;

    @Override
    public List<ReviewDTO> getReviews(Long mno) {
        Movie movie = Movie.builder().mno(mno).build();

        List<Review> result = reviewRepository.findByMovie(movie);

        return result.stream().map(review -> entityToDto(review)).collect(Collectors.toList());
    }

    @Override
    public ReviewDTO getReview(Long reviewNo) {
        return entityToDto(reviewRepository.findById(reviewNo).get());
    }

    @Override
    public Long addReview(ReviewDTO reviewDTO) {
        Review review = dtoToEntity(reviewDTO);

        return reviewRepository.save(review).getReviewNo();
    }

    @Override
    public Long modifyReview(ReviewDTO reviewDTO) {
        Review review = reviewRepository.findById(reviewDTO.getReviewNo()).get();

        review.setText(reviewDTO.getText());
        review.setGrade(reviewDTO.getGrade());
        return reviewRepository.save(review).getReviewNo();
    }

    @Override
    public void removeReview(Long reviewNo) {
        reviewRepository.deleteById(reviewNo);
    }

}
