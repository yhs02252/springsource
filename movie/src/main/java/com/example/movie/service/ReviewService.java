package com.example.movie.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.movie.dto.ReviewDTO;
import com.example.movie.entity.Member;
import com.example.movie.entity.Movie;
import com.example.movie.entity.Review;

@Service
public interface ReviewService {

    // movie 번호를 이용해 특정 영화의 모든 리뷰 조회
    List<ReviewDTO> getReviews(Long mno);

    // 특정 리뷰 조회
    ReviewDTO getReview(Long reviewNo);

    // 리뷰 등록
    Long addReview(ReviewDTO reviewDTO);

    // 리뷰 수정
    Long modifyReview(ReviewDTO reviewDTO);

    // 리뷰 삭제
    void removeReview(Long reviewNo);

    // Entity -> DTO
    default ReviewDTO entityToDto(Review review) {
        return ReviewDTO.builder()
                .reviewNo(review.getReviewNo())
                .grade(review.getGrade())
                .text(review.getText())
                .movieNo(review.getMovie().getMno())
                .memberId(review.getMember().getMid())
                .memberEmail(review.getMember().getEmail())
                .memberNickname(review.getMember().getNickName())
                .regDate(review.getCreatedDateTime())
                .upDateTime(review.getLastModifiedDateTime())
                .build();
    }

    // DTO -> Entity
    default Review dtoToEntity(ReviewDTO reviewDTO) {
        Movie movie = Movie.builder().mno(reviewDTO.getMovieNo()).build();
        Member member = Member.builder().mid(reviewDTO.getMemberId()).build();

        return Review.builder()
                .reviewNo(reviewDTO.getReviewNo())
                .grade(reviewDTO.getGrade())
                .text(reviewDTO.getText())
                .movie(movie)
                .member(member)
                .build();
    }
}
