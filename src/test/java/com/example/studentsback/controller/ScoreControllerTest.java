package com.example.studentsback.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.studentsback.model.entity.Course;
import com.example.studentsback.model.entity.Score;
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

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
class ScoreControllerTest {

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
        String username = "scr_" + System.currentTimeMillis();
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
        // 先创建依赖数据：学生
        Student stu = new Student();
        stu.setStudentNumber("SCSTU" + System.currentTimeMillis());
        stu.setName("成绩测试学生_" + System.currentTimeMillis());
        stu.setGender("男");
        stu.setClassName("测试班");
        stu.setPhone("13800000000");
        stu.setEmail("scstu@test.com");
        String stuResp = mockMvc.perform(post("/api/students")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(stu)))
                .andReturn().getResponse().getContentAsString();

        // 先创建依赖数据：课程
        Course c = new Course();
        c.setCourseName("成绩测试课程_" + System.currentTimeMillis());
        c.setCourseNumber("SCCN" + System.currentTimeMillis());
        c.setCredit(3.0);
        c.setTeacher("测试教师");
        c.setSemester(20241);
        String cResp = mockMvc.perform(post("/api/courses")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(c)))
                .andReturn().getResponse().getContentAsString();

        // 获取自增 ID（通过查询最新记录）
        String listStuResp = mockMvc.perform(get("/api/students?page=1&pageSize=1")
                        .header("Authorization", "Bearer " + token))
                .andReturn().getResponse().getContentAsString();
        Map<String, Object> listStu = objectMapper.readValue(listStuResp, Map.class);
        Integer studentId = null;
        if (listStu.get("data") instanceof java.util.List) {
            java.util.List<Map<String, Object>> data = (java.util.List<Map<String, Object>>) listStu.get("data");
            if (!data.isEmpty()) studentId = (Integer) data.get(0).get("id");
        }

        String listCrResp = mockMvc.perform(get("/api/courses/list")
                        .header("Authorization", "Bearer " + token))
                .andReturn().getResponse().getContentAsString();
        Map<String, Object> listCr = objectMapper.readValue(listCrResp, Map.class);
        Integer courseId = null;
        if (listCr.get("data") instanceof java.util.List) {
            java.util.List<Map<String, Object>> data = (java.util.List<Map<String, Object>>) listCr.get("data");
            if (!data.isEmpty()) courseId = (Integer) data.get(0).get("id");
        }

        if (studentId == null || courseId == null) {
            System.out.println("skip: no studentId or courseId");
            return;
        }

        Score s = new Score();
        s.setStudentId(studentId);
        s.setCourseId(courseId);
        s.setScore(85.5);

        String content = mockMvc.perform(post("/api/scores")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(s)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        System.out.println("Score insert response: " + content);
        Map<String, Object> result = objectMapper.readValue(content, Map.class);
        assertEquals(200, result.get("code"), "插入失败: " + result.get("message"));
    }

    @Test
    void getByStudentId() throws Exception {
        mockMvc.perform(get("/api/scores/student/1")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200));
    }

    @Test
    void getByCourseId() throws Exception {
        mockMvc.perform(get("/api/scores/course/1")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200));
    }

    @Test
    void searchByCondition() throws Exception {
        mockMvc.perform(get("/api/scores/search?studentId=1&courseId=1")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200));
    }

    @Test
    void getCourseAvg() throws Exception {
        mockMvc.perform(get("/api/scores/course/1/avg")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200));
    }

    @Test
    void getStudentAvg() throws Exception {
        mockMvc.perform(get("/api/scores/student/1/avg")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200));
    }

    @Test
    void getCourseMax() throws Exception {
        mockMvc.perform(get("/api/scores/course/1/max")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200));
    }

    @Test
    void getPassRate() throws Exception {
        mockMvc.perform(get("/api/scores/course/1/passRate")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200));
    }

    @Test
    void getExcellentRate() throws Exception {
        mockMvc.perform(get("/api/scores/course/1/excellentRate")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200));
    }

    @Test
    void getDistribution() throws Exception {
        mockMvc.perform(get("/api/scores/course/1/distribution")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200));
    }

    @Test
    void getStudentStatistics() throws Exception {
        mockMvc.perform(get("/api/scores/student/1/statistics")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200));
    }
}
