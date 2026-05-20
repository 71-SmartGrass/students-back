package com.example.studentsback.mapper;

import com.example.studentsback.model.entity.Student;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface StudentMapper {

    // 1. 分页条件查询
    @Select("<script>" +
            "SELECT * FROM student WHERE 1=1" +
            "<if test='name != null and name != \"\"'>" +
            " AND name LIKE CONCAT('%', #{name}, '%')" +
            "</if>" +
            "<if test='studentNumber != null and studentNumber != \"\"'>" +
            " AND student_number LIKE CONCAT('%', #{studentNumber}, '%')" +
            "</if>" +
            " ORDER BY id DESC" +
            " LIMIT #{offset}, #{pageSize}" +
            "</script>")
    List<Student> getStudents(@Param("offset") int offset,
                              @Param("pageSize") int pageSize,
                              @Param("name") String name,
                              @Param("studentNumber") String studentNumber);

    // 2. 查询总数
    @Select("<script>" +
            "SELECT COUNT(*) FROM student WHERE 1=1" +
            "<if test='name != null and name != \"\"'>" +
            " AND name LIKE CONCAT('%', #{name}, '%')" +
            "</if>" +
            "<if test='studentNumber != null and studentNumber != \"\"'>" +
            " AND student_number LIKE CONCAT('%', #{studentNumber}, '%')" +
            "</if>" +
            "</script>")
    long getTotalCount(@Param("name") String name, @Param("studentNumber") String studentNumber);

    // 3. 插入学生
    @Insert("INSERT INTO student(student_number, name, gender, class_name, phone, avatar) " +
            "VALUES(#{studentNumber}, #{name}, #{gender}, #{className}, #{phone}, #{avatar})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insertStudent(Student student);

    // 4. 更新学生
    @Update("UPDATE student SET " +
            "name = #{name}, " +
            "gender = #{gender}, " +
            "class_name = #{className}, " +
            "phone = #{phone}, " +
            "avatar = #{avatar} " +
            "WHERE id = #{id}")
    int updateStudent(Student student);

    // 5. 删除学生
    @Delete("DELETE FROM student WHERE id = #{id}")
    int deleteStudent(int id);

    // 6. 根据ID查询学生
    @Select("SELECT * FROM student WHERE id = #{id}")
    Student getStudentById(int id);

    // 7. 查询所有学生
    @Select("SELECT * FROM student ORDER BY id DESC")
    List<Student> getAllStudents();

    // 8. 批量删除
    @Delete("<script>" +
            "DELETE FROM student WHERE id IN " +
            "<foreach collection='ids' item='id' open='(' separator=',' close=')'>" +
            "#{id}" +
            "</foreach>" +
            "</script>")
    int deleteBatch(@Param("ids") List<Integer> ids);
}
