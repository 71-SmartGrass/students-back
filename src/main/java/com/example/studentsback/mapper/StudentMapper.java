package com.example.studentsback.mapper;

import com.example.studentsback.model.entity.Student;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface StudentMapper {
    // XML 映射的方法
    List<Student> getStudents(@Param("offset")  int offset,
                              @Param("pageSize") int pageSize,
                              @Param("name") String name,
                              @Param("studentNumber") String studentNumber);

    long getTotalCount(@Param("name") String name, @Param("studentNumber") String studentNumber);
    int insertStudent(Student student);
    int updateStudent(Student student);
    int deleteStudent(int id);
    Student getStudentById(int id);
    List<Student> getAllStudents();

    // 批量删除
    int deleteBatch(@Param("ids") List<Integer> ids);
}
