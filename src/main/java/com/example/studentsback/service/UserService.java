package com.example.studentsback.service;

import com.example.studentsback.model.entity.User;

public interface UserService {

    // 根据用户名查询
    User selectByUsername(String username);

    // 根据ID查询
    User selectById(Integer id);

    // 新增用户
    int insert(User user);

    // 修改密码
    int updatePassword(User user);

    // 登录验证
    User login(String username, String password);
}