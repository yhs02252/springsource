package com.example.movie.dto;

import java.time.LocalDateTime;

import com.example.movie.entity.constant.MemberRole;

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

    private String email;

    private String password;

    private String nickName;

    private MemberRole memberRole;

    private LocalDateTime regDate;

    private LocalDateTime upDateTime;
}
