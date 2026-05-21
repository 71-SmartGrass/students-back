package com.example.studentsback.model.entity;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class Student {
    private Integer id;
    private String studentNumber; //学号
    private String name;
    private String gender;
    private String className;
    private String phone;
    private String email; 
    private String avatar; // 头像
    private LocalDateTime createTime;
    private LocalDateTime updateTime;

}
