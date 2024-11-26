package com.example.movie.repository;

import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.movie.entity.Member;
import com.example.movie.entity.Movie;
import com.example.movie.entity.Review;

@SpringBootTest
public class ReviewRepositoryTest {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Test
    public void testMovieReviewInsert() {

        IntStream.rangeClosed(1, 200).forEach(i -> {

            long random = (long) (Math.random() * 50) + 1;
            long random2 = (long) (Math.random() * 50) + 1;
            int grade = (int) (Math.random() * 5) + 1;
            Movie movie = movieRepository.findById(random).get();
            Member member = memberRepository.findById(random2).get();

            Review review = Review.builder()
                    .grade(grade)
                    .text("review" + i)
                    .movie(movie)
                    .member(member)
                    .build();
            reviewRepository.save(review);
        });
    }
}