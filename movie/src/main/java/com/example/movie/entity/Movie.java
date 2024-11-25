package com.example.movie.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@SequenceGenerator(name = "movie_seq_gen", sequenceName = "movie_seq", allocationSize = 1)
public class Movie extends BaseEntity {

    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "movie_seq_gen")
    @Column(name = "movie_id")
    @Id
    private Long mno;

    @Column(nullable = false)
    private String title;

}
