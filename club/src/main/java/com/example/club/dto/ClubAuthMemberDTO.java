package com.example.club.dto;

import java.util.Collection;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.core.user.OAuth2User;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

// UserDetails <----- User <----- ClubAuthMemberDTO

@Getter
@Setter
@ToString
public class ClubAuthMemberDTO extends User implements OAuth2User {

    private String email; // 아이디 역할

    private String name;

    private boolean fromSocial;

    private String password; // 기존 회원 정보를 담기위해 추가

    private Map<String, Object> attr; // 소셜로그인 에서 넘어오는 값 담기

    // security 에서는 username == id
    public ClubAuthMemberDTO(String username, String password, boolean fromSocial,
            Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.email = username;
        this.password = password;
        this.fromSocial = fromSocial;
    }

    public ClubAuthMemberDTO(String username, String password, boolean fromSocial,
            Collection<? extends GrantedAuthority> authorities, Map<String, Object> attr) {
        this(username, password, fromSocial, authorities);
        this.attr = attr;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return this.attr;
    }
}
