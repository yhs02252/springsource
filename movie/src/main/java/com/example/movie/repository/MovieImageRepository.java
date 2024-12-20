package com.example.movie.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.example.movie.entity.MovieImage;
import com.example.movie.repository.total.MovieImageReviewRepository;
import com.example.movie.entity.Movie;

public interface MovieImageRepository extends JpaRepository<MovieImage, Long>, MovieImageReviewRepository {

    // movie mno 를 이용하여 movie image 를 제거하는 메소드 생성
    @Modifying
    @Query("DELETE FROM MovieImage mi WHERE mi.movie = :movie")
    void deleteByMovie(Movie movie);

    @Query(value = "SELECT * FROM MOVIE_IMAGE mi WHERE mi.PATH = TO_CHAR(SYSDATE-1, 'yyyy/mm/dd')", nativeQuery = true)
    List<MovieImage> findOldFileAll();
}
