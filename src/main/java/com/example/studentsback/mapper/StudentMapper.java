package com.example.studentsback.mapper;

import com.example.studentsback.model.entity.Student;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface StudentMapper {
    List<Student> getStudents(@Param("offset")  int offset, // 偏移量
                              @Param("pageSize") int pageSize, // 每页数量
                              @Param("name") String name, // 姓名
                              @Param("studentNumber") String studentNumber); // 学号

    long getTotalCount(@Param("name") String name, @Param("studentNumber") String studentNumber); // 获取总记录数
    int insertStudent(Student student); // 插入学生
    int updateStudent(Student student); // 更新学生
    int deleteStudent(int id); // 删除学生
    Student getStudentById(int id); // 根据ID查询学生
    List<Student> getAllStudents(); // 查询所有学生
}
