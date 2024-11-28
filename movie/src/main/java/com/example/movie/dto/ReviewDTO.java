package com.example.movie.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReviewDTO {

    private Long reviewNo;

    private int grade;

    private String text;

    private Long movieNo;

    private Long memberId;

    private String memberEmail;

    private String memberNickname;

    private LocalDateTime regDate;

    private LocalDateTime upDateTime;
}
