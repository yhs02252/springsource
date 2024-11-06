package com.example.memo.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemoDTO {

    private Long mno;

    @NotBlank(message = "내용 입력")
    private String memoText;

    private LocalDateTime createdDateTime;

    private LocalDateTime lastModifiedDateTime;
}
