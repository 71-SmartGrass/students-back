package com.example.studentsback.model.entity;

import java.time.LocalDateTime;

public class Course {
    private Integer id;
    private String courseName;
    private String courseNumber;
    private Double credit;
    private String teacher;
    private Integer semester; //学期
    private LocalDateTime createTime;

    public Integer getId() {return id;}
    public void setId(Integer id) {this.id = id;}
    public String getCourseName() {return courseName;}
    public void setCourseName(String courseName) {this.courseName = courseName;}
    public String getCourseNumber() {return courseNumber;}
    public void setCourseNumber(String courseNumber) {this.courseNumber = courseNumber;}
    public Double getCredit() {return credit;}
    public void setCredit(Double credit) {this.credit = credit;}
    public String getTeacher() {return teacher;}
    public void setTeacher(String teacher) {this.teacher = teacher;}
    public Integer getSemester() {return semester;}
    public void setSemester(Integer semester) {this.semester = semester;}
    public LocalDateTime getCreateTime() {return creatTime;}
    public void setCreatTime(LocalDateTime createTime) {this.creatTime = creatTime;}
}
