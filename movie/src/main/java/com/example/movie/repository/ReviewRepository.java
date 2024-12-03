package com.example.movie.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;

import com.example.movie.entity.Movie;
import com.example.movie.entity.Review;
import java.util.List;
import com.example.movie.entity.Member;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    // movie mno 를 이용하여 review 를 제거하는 메소드 생성
    @Modifying
    @Query("DELETE FROM Review r WHERE r.movie = :movie")
    void deleteByMovie(Movie movie);

    // movie mno 를 이용하여 review 를 제거하는 메소드 생성
    @EntityGraph(attributePaths = "member", type = EntityGraphType.FETCH)
    List<Review> findByMovie(Movie movie);

    @Modifying
    @Query("DELETE FROM Review r WHERE r.member = :member")
    void deleteByMember(Member member);
}
