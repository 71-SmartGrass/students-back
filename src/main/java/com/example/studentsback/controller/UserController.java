package com.example.studentsback.controller;

import com.example.studentsback.common.Result;
import com.example.studentsback.model.entity.User;
import com.example.studentsback.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // 登录
    @PostMapping("/login")
    public Result<User> login(@RequestParam String username, @RequestParam String password) {
        User user = userService.login(username, password);
        if (user != null) {
            return Result.success(user);
        } else {
            return Result.error(401, "用户名或密码错误");
        }
    }

    // 根据用户名查询
    @GetMapping("/username/{username}")
    public Result<User> getByUsername(@PathVariable String username) {
        User user = userService.selectByUsername(username);
        return Result.success(user);
    }

    // 根据ID查询
    @GetMapping("/{id}")
    public Result<User> getById(@PathVariable Integer id) {
        User user = userService.selectById(id);
        return Result.success(user);
    }

    // 新增用户
    @PostMapping
    public Result<Integer> add(@RequestBody User user) {
        int result = userService.insert(user);
        return Result.success(result);
    }

    // 修改密码
    @PutMapping("/password")
    public Result<Integer> updatePassword(@RequestBody User user) {
        int result = userService.updatePassword(user);
        return Result.success(result);
    }
}