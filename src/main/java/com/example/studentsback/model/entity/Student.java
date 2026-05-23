package com.example.studentsback.model.entity;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class Student {
    private Integer id;

    @NotBlank(message = "学号不能为空")
    private String studentNumber;

    @NotBlank(message = "姓名不能为空")
    private String name;

    private String gender;
    private String className;
    private String phone;

    @Email(message = "邮箱格式不正确")
    private String email;

    private String avatar;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;

}
