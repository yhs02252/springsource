package com.example.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity // 웹에 적용할 security 클래스 포함
@Configuration // 환경설정 파일 지정
public class SecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/", "/sample/guest").permitAll() // 전체 나열할 경로를 permit
                        .requestMatchers("/sample/member").hasRole("USER") // _<┓
                        .requestMatchers("/sample/admin").hasRole("ADMIN")) // <┛ 추가로 필요한 경로 권한 설정
                .formLogin(Customizer.withDefaults());

        return http.build();
    }

    @Bean // spring 컨테이너가 관리하는 객체 (다른 곳에서 passwordEncoder가 필요할 때 자동으로 주입)
    PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    };

    @Bean
    UserDetailsService users() {
        UserDetails user = User.builder()
                .username("user1")
                .password("{bcrypt}$2a$10$4vv.atapn1OXmz/3reIz5uImlh3NK5tqHEge7.w/Cbfv4vKkhlNwu")
                .roles("USER")
                .build();
        return new InMemoryUserDetailsManager(user);
    }
}
