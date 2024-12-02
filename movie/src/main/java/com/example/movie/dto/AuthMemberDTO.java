package com.example.movie.dto;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AuthMemberDTO extends User {

    private MemberDTO memberDTO;

    public AuthMemberDTO(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

    public AuthMemberDTO(MemberDTO memberDTO) {
        this(memberDTO.getEmail(), memberDTO.getPassword(),
                List.of(new SimpleGrantedAuthority("ROLE_" + memberDTO.getMemberRole())));
        this.memberDTO = memberDTO;
    }
}
