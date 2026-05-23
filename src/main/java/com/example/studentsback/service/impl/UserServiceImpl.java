package com.example.studentsback.service.impl;

import com.example.studentsback.mapper.UserMapper;
import com.example.studentsback.model.entity.User;
import com.example.studentsback.service.UserService;
import com.example.studentsback.util.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public UserServiceImpl(UserMapper userMapper, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public User selectByUsername(String username) {
        return userMapper.selectByUsername(username);
    }

    @Override
    public User selectById(Integer id) {
        return userMapper.selectById(id);
    }

    @Override
    public int insert(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userMapper.insert(user);
    }

    // 修改密码 — 新密码写入前需 BCrypt 加密，与 insert 保持一致
    @Override
    public int updatePassword(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userMapper.updatePassword(user);
    }

    /**
     * 用户登录
     *
     * 流程：查用户 → BCrypt 比对密码 → 签发 JWT Token
     */
    @Override
    public String login(String username, String password) {
        User user = userMapper.selectByUsername(username);
        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            return jwtUtil.generateToken(user.getId(), user.getUsername());
        }
        return null;
    }
}
