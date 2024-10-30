package com.example.project2.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@SequenceGenerator(name = "pro_board_seq_gen", sequenceName = "pro_board_seq", allocationSize = 1)
@Entity
public class ProBoard {

    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pro_board_seq_gen")
    @Id
    private Long id;

    @Column(name = "title", nullable = false, length = 100)
    private String title;

    @Column(name = "content", length = 1500, nullable = false)
    private String content;

    @Column(name = "writer", length = 50, nullable = false)
    private String writer;
}
