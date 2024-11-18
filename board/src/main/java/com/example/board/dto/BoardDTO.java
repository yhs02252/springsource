package com.example.board.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
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

    @NotBlank(message = "내용을 입력하세요")
    private String content;

    @NotBlank(message = "제목을 입력하세요")
    private String title;

    // private Member writer;
    // 쪼개기
    @NotBlank(message = "작성자를 입력하세요")
    @Email(message = "이메일 형식을 확인해 주세요")
    private String writerEmail;
    private String writerName;

    private LocalDateTime regDate;
    private LocalDateTime updateDate;

    // 댓글 개수
    private Long replyCnt;
}
