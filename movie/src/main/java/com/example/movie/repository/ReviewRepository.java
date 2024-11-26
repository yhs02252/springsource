package com.example.movie.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.example.movie.entity.Movie;
import com.example.movie.entity.Review;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    // movie mno 를 이용하여 review 를 제거하는 메소드 생성
    @Modifying
    @Query("DELETE FROM Review r WHERE r.movie = :movie")
    void deleteByMovie(Movie movie);

}
