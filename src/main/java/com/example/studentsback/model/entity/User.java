package com.example.studentsback.model.entity;

import java.time.LocalDateTime;

public class User {
    private Integer id;
    private String userName;
    private String password;
    private String email;
    private String role;
    private LocalDateTime createTime;

    public Integer getId() { return id ;}
    public void setId(Integer id) { this.id = id ;}
    public String getUserName() { return userName ;}
    public void setUserName(String userName) { this.userName = userName ;}
    public String getPassword() { return password ;}
    public void setPassword(String password) { this.password = password ;}
    public String getEmail() { return email ;}
    public void setEmail(String email) { this.email = email ;}
    public String getRole() { return role ;}
    public void setRole(String role) { this.role = role ;}
    public LocalDateTime getCreateTime() { return createTime ;}
    public void setCreateTime(LocalDateTime createTime) { this.createTime = createTime ;}
}
