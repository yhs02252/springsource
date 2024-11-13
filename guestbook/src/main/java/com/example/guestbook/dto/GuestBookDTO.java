package com.example.guestbook.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class GuestBookDTO {

    private Long gno;

    @NotBlank(message = "내용을 입력해 주세요")
    private String title;

    @NotBlank(message = "내용을 입력해 주세요")
    private String writer;

    @NotBlank(message = "내용을 입력해 주세요")
    private String content;

    private LocalDateTime createdDateTime;

    private LocalDateTime lastModifiedDateTime;
}
