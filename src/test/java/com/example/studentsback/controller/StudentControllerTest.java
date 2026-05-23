package com.example.studentsback.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.studentsback.model.entity.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
class StudentControllerTest {

    @Autowired
    private WebApplicationContext context;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private MockMvc mockMvc;
    private String token;

    @BeforeEach
    void setUp() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(SecurityMockMvcConfigurers.springSecurity()).build();
        if (token == null) {
            token = login();
        }
    }

    private String login() throws Exception {
        String username = "stu_" + System.currentTimeMillis();
        String regBody = objectMapper.writeValueAsString(
                Map.of("username", username, "password", "testpass"));
        mockMvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON).content(regBody));

        String loginBody = objectMapper.writeValueAsString(
                Map.of("username", username, "password", "testpass"));
        String response = mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON).content(loginBody))
                .andReturn().getResponse().getContentAsString();
        Map<String, Object> result = objectMapper.readValue(response, Map.class);
        Map<String, String> data = (Map<String, String>) result.get("data");
        return data.get("token");
    }

    @Test
    void insert() throws Exception {
        Student s = new Student();
        s.setStudentNumber("UT" + System.currentTimeMillis());
        s.setName("测试学生_" + System.currentTimeMillis());
        s.setGender("男");
        s.setClassName("测试班级");
        s.setPhone("13800000000");
        s.setEmail("test@test.com");

        String content = mockMvc.perform(post("/api/students")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(s)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        System.out.println("Student insert response: " + content);
        Map<String, Object> result = objectMapper.readValue(content, Map.class);
        assertEquals(200, result.get("code"), "插入失败: " + result.get("message"));
    }

    @Test
    void listPaged() throws Exception {
        mockMvc.perform(get("/api/students?page=1&pageSize=5")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200));
    }

    @Test
    void searchByName() throws Exception {
        mockMvc.perform(get("/api/students?name=测试")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200));
    }

    @Test
    void count() throws Exception {
        mockMvc.perform(get("/api/students/count")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200));
    }

    @Test
    void getById() throws Exception {
        mockMvc.perform(get("/api/students/1")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200));
    }

    @Test
    void update() throws Exception {
        Student s = new Student();
        s.setId(1);
        s.setName("更新后");
        s.setGender("女");
        s.setClassName("新班级");
        s.setPhone("13900000000");
        s.setEmail("updated@test.com");
        s.setStudentNumber("UPD" + System.currentTimeMillis());

        mockMvc.perform(put("/api/students/1")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(s)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200));
    }

    @Test
    void batchDelete() throws Exception {
        String body = objectMapper.writeValueAsString(List.of(99999));
        mockMvc.perform(delete("/api/students/batch")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200));
    }
}
