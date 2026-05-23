package com.example.studentsback.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.studentsback.model.entity.Course;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
class CourseControllerTest {

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
        String username = "crs_" + System.currentTimeMillis();
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
        Course c = new Course();
        c.setCourseName("测试课程" + System.currentTimeMillis());
        c.setCourseNumber("CN" + System.currentTimeMillis());
        c.setCredit(3.0);
        c.setTeacher("测试教师");
        c.setSemester(20241);

        mockMvc.perform(post("/api/courses")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(c)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200));
    }

    @Test
    void listAll() throws Exception {
        mockMvc.perform(get("/api/courses/list")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200));
    }

    @Test
    void page() throws Exception {
        mockMvc.perform(get("/api/courses/page?offset=0&size=5")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200));
    }

    @Test
    void search() throws Exception {
        mockMvc.perform(get("/api/courses/search?keyword=测试")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200));
    }

    @Test
    void getBySemester() throws Exception {
        mockMvc.perform(get("/api/courses/semester/20241")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200));
    }

    @Test
    void getByTeacher() throws Exception {
        mockMvc.perform(get("/api/courses/teacher/测试教师")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200));
    }

    @Test
    void count() throws Exception {
        mockMvc.perform(get("/api/courses/count")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200));
    }

    @Test
    void batchDelete() throws Exception {
        String body = objectMapper.writeValueAsString(List.of(99999));
        mockMvc.perform(delete("/api/courses/batch")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200));
    }
}
