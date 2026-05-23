package com.example.studentsback.controller;

import com.example.studentsback.common.Result;
import com.example.studentsback.model.entity.User;
import com.example.studentsback.service.UserService;
import org.springframework.web.bind.annotation.*;

/**
 * 用户管理控制器
 *
 * 提供用户基本 CRUD 操作（不含登录认证，登录已移至 AuthController）。
 *
 * 接口清单：
 * - GET    /api/users/{id}              根据 ID 查询用户
 * - GET    /api/users/username/{username} 根据用户名查询用户
 * - POST   /api/users                   新增用户（密码自动 BCrypt 加密）
 * - PUT    /api/users/password          修改密码
 */
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * 根据用户名查询用户
     */
    @GetMapping("/username/{username}")
    public Result<User> getByUsername(@PathVariable String username) {
        User user = userService.selectByUsername(username);
        return Result.success(user);
    }

    /**
     * 根据 ID 查询用户
     */
    @GetMapping("/{id}")
    public Result<User> getById(@PathVariable Integer id) {
        User user = userService.selectById(id);
        return Result.success(user);
    }

    /**
     * 新增用户
     *
     * 密码会自动使用 BCrypt 加密存储。
     */
    @PostMapping
    public Result<Integer> add(@RequestBody User user) {
        int result = userService.insert(user);
        return Result.success(result);
    }

    /**
     * 修改密码
     */
    @PutMapping("/password")
    public Result<Integer> updatePassword(@RequestBody User user) {
        int result = userService.updatePassword(user);
        return Result.success(result);
    }
}
