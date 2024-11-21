package com.example.club.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClubMemberDTO {

    @NotBlank(message = "이메일을 입력해 주세요")
    @Email
    private String email;

    @NotBlank(message = "비밀번호를 입력해 주세요")
    private String password;

    @NotBlank(message = "이름 입력해 주세요")
    private String name;

    private boolean fromSocial;
}
