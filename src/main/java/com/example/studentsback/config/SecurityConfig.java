package com.example.studentsback.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

@Configuration
public class SecurityConfig {
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/**").permitAll() // 认证接口 允许所有访问
                        .anyRequest().permitAll() // 允许其他请求
                )
                .csrf(csrf -> csrf.disable()) // 禁用 CSRF 保护 ，无状态API，不需要 CSRF 保护
                .formLogin(form -> form.disable()) // 禁用表单登录，无状态API，不需要表单登录
                .httpBasic(basic -> basic.disable()); // 禁用 HTTP 基本认证，无状态API，不需要 HTTP 基本认证

        return http.build();
    }
}
