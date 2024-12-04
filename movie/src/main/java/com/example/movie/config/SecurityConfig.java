package com.example.movie.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.example.movie.handler.CustomAccessDeniedHandler;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/", "/assets/**", "/css/**", "/js/**", "/upload/**").permitAll()
                        .requestMatchers("/movie/list", "/member/register").permitAll()
                        .requestMatchers("/member/createaccount", "/member/forgotpassword").permitAll()
                        .requestMatchers("/movie/modify").hasRole("ADMIN")
                        .anyRequest().authenticated());
        http.formLogin(login -> login.loginPage("/member/login").permitAll().defaultSuccessUrl("/movie/list"));
        // http.formLogin(login -> login.);

        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.ALWAYS));

        http.logout(logout -> logout
                .logoutRequestMatcher(new AntPathRequestMatcher("/member/logout"))
                .logoutSuccessUrl("/"));

        // http.csrf(csrf -> csrf.disable());

        // 403 처리 (정적(static) 페이지(accessDeniedPage)로 처리할 시)
        // http.exceptionHandling(exception ->
        // exception.accessDeniedPage("/accessdenied.html"));

        // 403 처리 (핸들러(accessDeniedHandler)로 처리할시)
        http.exceptionHandling(exception -> exception.accessDeniedHandler(customAccessDeniedHandler()));

        return http.build();

    }

    @Bean
    CustomAccessDeniedHandler customAccessDeniedHandler() {
        return new CustomAccessDeniedHandler();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
