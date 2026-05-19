package com.example.studentsback.mapper;

import com.example.studentsback.model.entity.Student;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface StudentMapper {
    // XML 映射
    List<Student> getStudents(@Param("offset")  int offset,
                              @Param("pageSize") int pageSize,
                              @Param("name") String name,
                              @Param("studentNumber") String studentNumber);

    long getTotalCount(@Param("name") String name, @Param("studentNumber") String studentNumber);//查询总数
    int insertStudent(Student student);//插入学生
    int updateStudent(Student student);//更新学生
    int deleteStudent(int id);//通过id删除学生
    Student getStudentById(int id);//通过id查询学生
    List<Student> getAllStudents();//查询所有学生
    int deleteBatch(@Param("ids") List<Integer> ids); // 批量删除
}
