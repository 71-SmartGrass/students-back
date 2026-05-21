package com.example.studentsback.mapper;

import com.example.studentsback.model.entity.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {
    // 根据用户名查询用户
    @Select("select * from users where username = #{username}")
    User selectByUsername(@Param("username") String username);

    // 插入用户
    @Insert("INSERT INTO users(username, password, role) VALUES(#{username}, #{password}, #{role})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(User user);

    // 根据ID查询用户
    @Select("SELECT * FROM users WHERE id = #{id}")
    User selectById(Integer id);

    // 更新用户
    @Update("UPDATE users SET password = #{password} WHERE id = #{id}")
    int updatePassword(User user);
}
