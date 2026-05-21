package com.example.studentsback.service;

import com.example.studentsback.model.entity.Student;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public interface StudentService {
    //获取学生(分页)
    //offset 偏移量
    //pageSize 每页数量
    //name 姓名
    //studentNumber 学号
    //return 所有学生
    List<Student> getStudents(int offset, int pageSize, String name, String studentNumber);

    long getTotalCount(String name, String studentNumber); //获取学生总数
    Student getStudentById(int id); //根据ID获取学生
    Student getStudentByStudentNumber(String studentNumber); //根据学号获取学生
    List<Student> getAllStudents(); //获取所有学生
    void insertStudent(Student student); //插入学生
    void updateStudent(Student student); //更新学生
    void deleteStudent(int id); //删除学生
    void deleteBatchStudents(List<Integer> ids); //批量删除学生 ids->学生ID列表
}
