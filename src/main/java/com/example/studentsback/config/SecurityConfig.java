package com.example.studentsback.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Spring Security 安全配置
 *
 * 配置全局安全策略，包括：
 * 1. 接口访问权限控制（哪些路径放行、哪些需要认证）
 * 2. JWT 无状态认证（禁用 Session）
 * 3. 注册 JWT 认证过滤器
 * 4. 密码编码器
 *
 * 架构说明：
 * - 前端 → HttpServletRequest → JwtAuthenticationFilter → SecurityFilterChain → Controller
 * - /api/auth/**（登录、注册）直接放行，不经过 JWT 过滤器
 * - 其他所有请求必须先通过 JWT 认证
 */
@Configuration
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    /**
     * 配置安全过滤器链
     *
     * 策略要点：
     * - /api/auth/**（登录、注册）完全公开，任何人可访问
     * - 其余所有接口必须携带有效 JWT Token
     * - 禁用 CSRF（纯 API 服务不需要）
     * - 禁用 Session，采用无状态模式（每个请求独立认证）
     * - 在 UsernamePasswordAuthenticationFilter 之前插入 JWT 过滤器
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // 1️⃣ 接口访问权限控制
            .authorizeHttpRequests(auth -> auth
                // 登录、注册等认证接口 — 完全放行，不需要 Token
                .requestMatchers("/api/auth/**").permitAll()
                // 除放行路径外的所有请求 — 必须经过认证
                .anyRequest().authenticated()
            )
            // 2️⃣ 安全防护配置
            .csrf(csrf -> csrf.disable())               // 禁用 CSRF（无状态 API 不需要）
            .formLogin(form -> form.disable())           // 禁用表单登录（使用 JWT 认证）
            .httpBasic(basic -> basic.disable())         // 禁用 HTTP Basic 认证
            // 3️⃣ 无状态 Session 策略 — 每次请求独立认证，不创建 Session
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            // 4️⃣ 注册 JWT 认证过滤器 — 在 Spring Security 默认认证过滤器之前执行
            .addFilterBefore(jwtAuthenticationFilter,
                UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    /**
     * 密码编码器
     *
     * 使用 BCrypt 算法对密码进行哈希处理。
     * BCrypt 自动加盐，每次编码结果不同，即使相同密码。
     * 用于：
     * - 用户注册时对明文密码加密存储
     * - 用户登录时将明文密码与数据库中哈希值比对
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
