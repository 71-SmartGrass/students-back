package com.example.studentsback.controller;

import com.example.studentsback.common.Result;
import com.example.studentsback.model.entity.User;
import com.example.studentsback.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 认证控制器
 *
 * 处理用户认证相关请求，包括登录和注册。
 * 所有接口均不需要 JWT Token（已在 JwtAuthenticationFilter 中放行 /api/auth/**）。
 *
 * 接口清单：
 * - POST /api/auth/login    登录，返回 JWT Token
 * - POST /api/auth/register 注册新用户
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    /**
     * 用户登录
     *
     * 校验用户名和密码，认证通过后签发 JWT Token。
     * Token 有效期 24 小时，后续请求需在 Authorization 头中携带。
     *
     * 请求体：
     * <pre>
     * {
     *   "username": "admin",
     *   "password": "123456"
     * }
     * </pre>
     *
     * 成功响应：
     * <pre>
     * { "code": 200, "message": "success", "data": { "token": "xxx" } }
     * </pre>
     *
     * 失败响应：
     * <pre>
     * { "code": 401, "message": "用户名或密码错误", "data": null }
     * </pre>
     */
    @PostMapping("/login")
    public Result<Map<String, String>> login(@RequestBody Map<String, String> params) {
        String username = params.get("username");
        String password = params.get("password");

        String token = userService.login(username, password);
        if (token != null) {
            return Result.success(Map.of("token", token));
        } else {
            return Result.error(401, "用户名或密码错误");
        }
    }

    /**
     * 用户注册
     *
     * 创建新用户，密码会自动使用 BCrypt 加密存储。
     * 默认角色为 "user"。
     *
     * 请求体：
     * <pre>
     * {
     *   "username": "admin",
     *   "password": "123456"
     * }
     * </pre>
     *
     * 成功响应：
     * <pre>
     * { "code": 200, "message": "success", "data": null }
     * </pre>
     */
    @PostMapping("/register")
    public Result<Void> register(@RequestBody Map<String, String> params) {
        String username = params.get("username");
        String password = params.get("password");

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setRole("user");

        userService.insert(user);
        return Result.success();
    }
}
