package com.example.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http) throws Exception {

        return http
                // 前后端通过 JSON 通信，暂时关闭 CSRF
                .csrf(AbstractHttpConfigurer::disable)

                // JWT 不依赖服务器 Session
                .sessionManagement(session ->
                        session.sessionCreationPolicy(
                                SessionCreationPolicy.STATELESS))

                // 设置接口访问规则
                .authorizeHttpRequests(authorize -> authorize
                        // 注册和登录不需要 Token
                        .requestMatchers("/register", "/login").permitAll()

                        // 其他所有接口都需要认证
                        .anyRequest().authenticated()
                )

                // 不使用浏览器弹窗式的 Basic 登录
                .httpBasic(AbstractHttpConfigurer::disable)

                // 不使用 Spring 默认登录页面
                .formLogin(AbstractHttpConfigurer::disable)

                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}