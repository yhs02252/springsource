package com.example.movie.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.movie.entity.Movie;

public interface MovieRepository extends JpaRepository<Movie, Long> {

    @Query("SELECT m, avg(r.grade), count(distinct r) FROM Movie m LEFT JOIN Review r ON r.movie = m GROUP BY m")
    Page<Object[]> getListPage(Pageable pageable);

    @Query("SELECT m, avg(r.grade), count(distinct r), max(inum), mi.path, mi.uuid FROM Movie m LEFT JOIN Review r ON r.movie = m JOIN MovieImage mi ON mi.movie = m GROUP BY m, mi.path,mi.uuid")
    Page<Object[]> getListPage2(Pageable pageable);
}
