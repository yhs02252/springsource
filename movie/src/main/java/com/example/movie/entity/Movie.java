package com.example.movie.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
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
@ToString(exclude = { "movieImages", "reviews" })
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

    // 자식 연관관계 추가(양방향)
    // @Builder.Default
    // @OneToMany(mappedBy = "movie", cascade = CascadeType.REMOVE)
    // // 객체 movie 를 기준으로 요소를 지울때 같이 지워지도록 연동
    // List<MovieImage> movieImages = new ArrayList<>();

    // @Builder.Default
    // @OneToMany(mappedBy = "movie", cascade = CascadeType.REMOVE)
    // // 객체 movie 를 기준으로 요소를 지울때 같이 지워지도록 연동
    // List<Review> reviews = new ArrayList<>();

}
