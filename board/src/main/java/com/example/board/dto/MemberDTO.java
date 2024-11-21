package com.example.board.dto;

import com.example.board.entity.MemberRole;

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
public class MemberDTO {

    @Email
    @NotBlank(message = "이메일은 필수요소 입니다")
    private String email;

    @NotBlank(message = "이름은 필수요소 입니다")
    private String name;

    @NotBlank(message = "비밀번호은 필수요소 입니다")
    private String password;

    private MemberRole role;

}
