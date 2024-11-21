package com.example.club.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
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
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.example.club.handler.ClubLoginSuccessHandler;

@EnableMethodSecurity // 접근 제한을 어노테이션으로 처리하기 위해 필요
@EnableWebSecurity
@Configuration
public class ConfigSecurity {

    @Bean
    ClubLoginSuccessHandler clubLoginSuccessHandler() {
        return new ClubLoginSuccessHandler();
    }

    // 시큐리티를 적용할 url 상세 설정 => Filter 등록
    @Bean
    SecurityFilterChain securituFilterChain(HttpSecurity http, RememberMeServices rememberMeServices) throws Exception {
        http
                // 기본적인 방식
                // .authorizeHttpRequests(authorize -> authorize
                // .requestMatchers("/", "/users/auth").permitAll()
                // .requestMatchers("/users/member").hasRole("USER")
                // .requestMatchers("/users/admin").hasAnyRole("USER", "MANAGER", "ADMIN"))
                // .formLogin(login -> login.loginPage("/users/login").permitAll()
                // .successHandler(clubLoginSuccessHandler()))

                // 접근 제한 메소드 사용 후 방식
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/img/*", "/js/*", "/css/*").permitAll() // <- static 아래 파일명을 써주면됨
                        .anyRequest().permitAll())

                .formLogin(
                        login -> login.loginPage("/users/login").permitAll() // @PreAuthorize 로 제한을 뒀을 경우 post 작업이 이뤄지지
                                                                             // 않아 loginPage 작업이 필요함
                                .successHandler(clubLoginSuccessHandler()))
                .oauth2Login(login -> login.successHandler(clubLoginSuccessHandler()))
                .logout(logout -> logout
                        .logoutRequestMatcher(new AntPathRequestMatcher("/users/logout"))
                        .logoutSuccessUrl("/"))
                .rememberMe(remember -> remember.rememberMeServices(rememberMeServices));
        return http.build();
    }

    // 비밀번호 암호화
    @Bean
    PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    // remember
    @Bean
    RememberMeServices rememberMeServices(UserDetailsService userDetailsService) {
        RememberMeTokenAlgorithm rMeTokenAlgorithm = RememberMeTokenAlgorithm.SHA256;
        TokenBasedRememberMeServices rememberMeServices = new TokenBasedRememberMeServices("myKey", userDetailsService,
                rMeTokenAlgorithm);
        rememberMeServices.setMatchingAlgorithm(RememberMeTokenAlgorithm.MD5);
        rememberMeServices.setTokenValiditySeconds(60 * 60 * 24 * 7);
        return rememberMeServices;
    }

    /*
     * ============= 클럽 설계 =============
     * 
     * 클럽 멤버
     * 
     * - USER, MANAGER, ADMIN : ROLE 관리
     * 
     * - 회원가입
     * email, password, name, fromSocial(소셜 가입 여부), role
     * 
     */
}
