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
public class ReplyDTO {

    private Long rno;

    private String replyer;

    private String text;

    // private Board board;
    private Long bno; // 게시글 번호(부모)

    private LocalDateTime regDate;
    private LocalDateTime updateDate;
}
