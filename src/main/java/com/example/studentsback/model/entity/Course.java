package com.example.studentsback.model.entity;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class Course {
    private Integer id;
    private String courseName;
    private String courseNumber;
    private Double credit;
    private String teacher;
    private Integer semester; //学期
    private LocalDateTime createTime;

}
