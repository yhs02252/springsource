package com.example.movie.dto;

import java.time.LocalDateTime;

import com.example.movie.entity.constant.MemberRole;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberDTO {

    private Long mid;

    @NotBlank(message = "이메일을 입력해주세요")
    private String email;

    @NotBlank(message = "비밀번호를 입력해주세요")
    private String password;

    @NotBlank(message = "닉네임을 입력해주세요")
    private String nickName;

    private MemberRole memberRole;

    private LocalDateTime regDate;

    private LocalDateTime upDateTime;
}
