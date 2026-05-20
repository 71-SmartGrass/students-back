package com.example.studentsback.model.entity;

import java.time.LocalDateTime;

public class Student {
    private Integer id;
    private String studentNumber; //学号
    private String name;
    private String gender;
    private String className;
    private String phone;
    private String avatar; // 头像
    private LocalDateTime createTime;
    private LocalDateTime updateTime;

    public Integer getId() { return id ;}
    public void setId(Integer id) { this.id = id ;}
    public String getStudentNumber() { return studentNumber ;}
    public void setStudentNumber(String studentNumber) { this.studentNumber = studentNumber ;}
    public String getName() { return name ;}
    public void setName(String name) { this.name = name ;}
    public String getGender() { return gender ;}
    public void setGender(String gender) { this.gender = gender ;}
    public String getClassName() { return className ;}
    public void setClassName(String className) { this.className = className ;}
    public String getPhone() { return phone ;}
    public void setPhone(String phone) { this.phone = phone ;}
    public String getAvatar() { return avatar ;}
    public void setAvatar(String avatar) { this.avatar = avatar ;}
    public LocalDateTime getCreateTime() { return createTime ;}
    public void setCreateTime(LocalDateTime createTime) { this.createTime = createTime ;}
    public LocalDateTime getUpdateTime() { return updateTime ;}
    public void setUpdateTime(LocalDateTime updateTime) { this.updateTime = updateTime ;}
}
