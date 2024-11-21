package com.example.board.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices.RememberMeTokenAlgorithm;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        // static 아래 폴더, 필터 무조건 통과 => 컨트롤러에서 접근 제한 설정
        http.authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/img/*", "/css/*", "/js/*", "/assets/*").permitAll()
                .anyRequest().permitAll());

        http.formLogin(login -> login.loginPage("/member/login").permitAll());

        return http.build();
    }

    // Remember Me 토큰
    // @Bean
    // RememberMeServices rememberMeServices(UserDetailsService userDetailsService)
    // {
    // RememberMeTokenAlgorithm rMeTokenAlgorithm = RememberMeTokenAlgorithm.SHA256;
    // TokenBasedRememberMeServices rememberMeServices = new
    // TokenBasedRememberMeServices("myKey", userDetailsService,
    // rMeTokenAlgorithm);
    // rememberMeServices.setMatchingAlgorithm(RememberMeTokenAlgorithm.MD5);
    // rememberMeServices.setTokenValiditySeconds(60 * 60 * 24 * 7);
    // return rememberMeServices;
    // }

    // 비밀번호 암호화
    @Bean
    PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
