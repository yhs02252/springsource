package com.example.project2.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.project2.entity.ProBoard;

public interface BoardRepository extends JpaRepository<ProBoard, Long> {

    // where title = ?
    List<ProBoard> findByTitle(String title);

    // where title like ?
    List<ProBoard> findByTitleLike(String title);

}
