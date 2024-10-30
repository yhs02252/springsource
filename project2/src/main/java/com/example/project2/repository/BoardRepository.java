package com.example.project2.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.project2.entity.ProBoard;

public interface BoardRepository extends JpaRepository<ProBoard, Long> {

}
