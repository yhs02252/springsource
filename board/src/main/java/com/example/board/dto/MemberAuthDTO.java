package com.example.board.dto;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
// @Builder
public class MemberAuthDTO extends User {

    private MemberDTO memberDTO;

    public MemberAuthDTO(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

    public MemberAuthDTO(MemberDTO memberDTO) {

        this(memberDTO.getEmail(), memberDTO.getPassword(),
                List.of(new SimpleGrantedAuthority("ROLE_" + memberDTO.getRole().toString())));
        this.memberDTO = memberDTO;
    }
}
