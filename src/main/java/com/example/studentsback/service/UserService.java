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

    /**
     * 用户登录
     *
     * 校验用户名和密码，认证通过后签发 JWT Token。
     *
     * @param username 用户名
     * @param password 明文密码
     * @return JWT Token 字符串，认证失败返回 null
     */
    String login(String username, String password);
}