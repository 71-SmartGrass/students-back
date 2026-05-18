package com.example.studentsback.model.entity;

import java.time.LocalDateTime;

public class Score {
    private Integer id;
    private Integer studentId;
    private Integer courseIdId;
    private Double score;
    private LocalDateTime creatTime;

    //外键
    private String studentName;
    private String studentNumber;
    private String courseName;
    private String courseNumber;
    private Double credit;
    private String teacher;
    private Integer semester; //学期
    
    public Integer getId() {return id;}
    public void setId(Integer id) {this.id = id;}
    public Integer getStudentId() {return studentId;}
    public void setStudentId(Integer studentId) {this.studentId = studentId;}
    public Integer getCourseIdId() {return courseIdId;}
    public void setCourseIdId(Integer courseIdId) {this.courseIdId = courseIdId;}
    public Double getScore() {return score;}
    public void setScore(Double score) {this.score = score;}
    public LocalDateTime getCreatTime() {return creatTime;}
    public void setCreatTime(LocalDateTime creatTime) {this.creatTime = creatTime;}
    public String getStudentName() {return studentName;}
    public void setStudentName(String studentName) {this.studentName = studentName;}
    public String getStudentNumber() {return studentNumber;}
    public void setStudentNumber(String studentNumber) {this.studentNumber = studentNumber;}
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
}
