package com.example.studentsback.model.entity;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class Score {
    private Integer id;
    private Integer studentId;
    private Integer courseId;
    private Double score;
    private LocalDateTime createTime;

    //外键
    private String studentName;
    private String studentNumber;
    private String courseName;
    private String courseNumber;
    private Double credit;
    private String teacher;
    private Integer semester; //学期

}
