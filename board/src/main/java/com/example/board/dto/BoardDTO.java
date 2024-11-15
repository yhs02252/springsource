package com.example.board.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BoardDTO {

    private Long bno;
    private String content;
    private String title;

    // private Member writer;
    // 쪼개기
    private String writerEmail;
    private String writerName;

    private LocalDateTime regDate;
    private LocalDateTime updateDate;

    // 댓글 개수
    private Long replyCnt;
}
