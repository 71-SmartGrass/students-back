package com.example.studentsback.mapper;

import com.example.studentsback.model.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
    // 根据用户名查询用户
    @Select("select * from users where username = #{username}")
    User selectByUsername(@Param("username") String username);
}
