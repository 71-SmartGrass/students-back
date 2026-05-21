package com.example.studentsback.model.entity;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class User {
    private Integer id;
    private String username;
    private String password;
    private String role;
    private LocalDateTime createTime;

}
