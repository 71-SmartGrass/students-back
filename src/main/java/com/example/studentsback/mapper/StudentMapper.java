package com.example.studentsback.mapper;

import com.example.studentsback.model.entity.Student;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface StudentMapper {
    
    // 插入学生
    @Insert("INSERT INTO student(stu_number, stu_name, gender, class_name, phone, avatar) " +
            "VALUES(#{stuNumber}, #{stuName}, #{gender}, #{className}, #{phone}, #{avatar})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Student student);
    
    // 删除学生
    @Delete("DELETE FROM student WHERE id = #{id}")
    int deleteById(Integer id);
    
    // 批量删除
    @Delete("<script>" +
            "DELETE FROM student WHERE id IN " +
            "<foreach collection='ids' item='id' open='(' separator=',' close=')'>" +
            "#{id}" +
            "</foreach>" +
            "</script>")
    int deleteBatch(@Param("ids") List<Integer> ids);
    
    // 更新学生
    @Update("UPDATE student SET stu_name=#{stuName}, gender=#{gender}, " +
            "class_name=#{className}, phone=#{phone}, avatar=#{avatar} " +
            "WHERE id=#{id}")
    int update(Student student);
    
    // 根据ID查询
    @Select("SELECT * FROM student WHERE id = #{id}")
    Student selectById(Integer id);
    
    // 根据学号查询
    @Select("SELECT * FROM student WHERE stu_number = #{stuNumber}")
    Student selectByStuNumber(String stuNumber);
    
    // 条件分页查询
    @Select("<script>" +
            "SELECT * FROM student WHERE 1=1" +
            "<if test='keyword != null and keyword != \"\"'>" +
            " AND (stu_name LIKE CONCAT('%', #{keyword}, '%') OR stu_number LIKE CONCAT('%', #{keyword}, '%'))" +
            "</if>" +
            " ORDER BY id DESC" +
            "</script>")
    List<Student> selectByCondition(@Param("keyword") String keyword);
    
    // 查询总数
    @Select("<script>" +
            "SELECT COUNT(*) FROM student WHERE 1=1" +
            "<if test='keyword != null and keyword != \"\"'>" +
            " AND (stu_name LIKE CONCAT('%', #{keyword}, '%') OR stu_number LIKE CONCAT('%', #{keyword}, '%'))" +
            "</if>" +
            "</script>")
    Long countByCondition(@Param("keyword") String keyword);
    
    // 查询所有
    @Select("SELECT * FROM student ORDER BY id DESC")
    List<Student> selectAll();
}
