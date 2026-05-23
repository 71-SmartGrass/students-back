package com.example.studentsback.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * CORS 跨域配置
 * <p>
 * 允许前端应用（如 Vite 开发服务器 http://localhost:5173）跨域访问后端 API。
 * 如果不配置此文件，浏览器会因同源策略拦截前端发起的请求。
 *
 * 配置说明：
 *   - allowedOrigins:   允许的前端来源，生产环境应替换为实际域名
 *   - allowedMethods:   允许的 HTTP 方法
 *   - allowedHeaders:   允许的请求头（含 Authorization 头用于 JWT 认证）
 *   - allowCredentials: 允许携带 Cookie 等凭证
 */
@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOrigins("http://localhost:5173")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH")
                .allowedHeaders("*")
                .allowCredentials(true);
    }
}
