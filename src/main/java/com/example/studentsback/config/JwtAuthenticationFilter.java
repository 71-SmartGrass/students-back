package com.example.studentsback.config;

import com.example.studentsback.util.JwtUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

/**
 * JWT 认证过滤器
 *
 * 继承 OncePerRequestFilter 确保每次请求只执行一次过滤逻辑。
 * 职责：
 * 1. 从请求头中提取 JWT Token
 * 2. 校验 Token 的有效性（格式、签名、过期时间）
 * 3. 解析 Token 后将用户信息注入 Spring Security 上下文
 * 4. Token 无效时直接返回 401 响应
 *
 * 白名单路径（/api/auth/**）直接放行，不做 JWT 校验。
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    // 白名单路径前缀 — 登录、注册等接口不需要 Token
    private static final String AUTH_PREFIX = "/api/auth/";
    // HTTP 请求头名称
    private static final String TOKEN_HEADER = "Authorization";
    // Bearer Token 前缀（注意 Bearer 后面有一个空格）
    private static final String TOKEN_PREFIX = "Bearer ";

    public JwtAuthenticationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    /**
     * 判断当前请求是否不需要执行过滤逻辑
     *
     * 返回 true 表示跳过 doFilterInternal，直接放行。
     * 这里对 /api/auth/ 开头的路径（登录、注册）跳过 JWT 校验。
     */
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        return path.startsWith(AUTH_PREFIX);
    }

    /**
     * 核心过滤逻辑 — 每次请求都会执行（除非 shouldNotFilter 返回 true）
     *
     * 流程：
     * 1. 从 Authorization 请求头提取 Bearer Token
     * 2. 调用 JwtUtil 解析 Token 获取 Claims
     * 3. 检查 Token 是否过期
     * 4. 从 Claims 中提取 userId 和 username
     * 5. 构造 UsernamePasswordAuthenticationToken 注入 SecurityContextHolder
     * 6. 放行请求到后续过滤器或目标接口
     *
     * 任何一步失败都直接返回 401 JSON 响应，不继续执行过滤器链。
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        // 1️⃣ 从请求头中获取 Authorization 头
        String authHeader = request.getHeader(TOKEN_HEADER);

        // 2️⃣ 校验请求头是否存在且以 "Bearer " 开头
        if (!StringUtils.hasText(authHeader) || !authHeader.startsWith(TOKEN_PREFIX)) {
            writeUnauthorized(response, "token不能为空");
            return;
        }

        // 3️⃣ 截取 "Bearer " 之后的 Token 字符串
        String token = authHeader.substring(TOKEN_PREFIX.length());

        try {
            // 4️⃣ 解析 Token → 获取 Claims（包含用户信息、过期时间等）
            Claims claims = jwtUtil.parseToken(token);

            // 5️⃣ 检查 Token 是否已过期
            if (jwtUtil.isTokenExpired(claims.getExpiration())) {
                writeUnauthorized(response, "token已过期");
                return;
            }

            // 6️⃣ 从 Claims 中提取用户信息
            Integer userId = Integer.valueOf(claims.getSubject());
            String username = claims.get("username", String.class);

            // 7️⃣ 构造 Spring Security 认证令牌并注入上下文
            //     principal = userId, credentials = username, authorities = 空列表（暂未使用角色）
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(
                            userId, username, Collections.emptyList());
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // 8️⃣ 放行请求，进入后续过滤器或目标 Controller
            filterChain.doFilter(request, response);

        } catch (ExpiredJwtException e) {
            // Token 已过期 — JWT 解析时抛出的特定异常
            writeUnauthorized(response, "token已过期");
        } catch (JwtException e) {
            // Token 格式错误 / 签名不匹配 / 被篡改等
            writeUnauthorized(response, "token无效");
        }
    }

    /**
     * 返回 401 未授权响应
     *
     * 手动构造 JSON 字符串写入响应体，与统一响应格式 Result 保持一致。
     * 客户端收到此响应后应跳转到登录页或重新获取 Token。
     */
    private void writeUnauthorized(HttpServletResponse response, String message) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json;charset=UTF-8");
        String json = String.format("{\"code\":401,\"message\":\"%s\",\"data\":null}", message);
        response.getWriter().write(json);
    }
}
