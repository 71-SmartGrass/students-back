package com.example.studentsback.mapper;

import com.example.studentsback.model.entity.Course;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface CourseMapper {
    
    // 1. 插入课程
    @Insert("INSERT INTO course(course_name, course_number, credit, teacher, semester) " +
            "VALUES(#{courseName}, #{courseNumber}, #{credit}, #{teacher}, #{semester})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Course course);
    
    // 2. 根据ID删除课程
    @Delete("DELETE FROM course WHERE id = #{id}")
    int deleteById(Integer id);
    
    // 3. 批量删除课程
    @Delete("<script>" +
            "DELETE FROM course WHERE id IN " +
            "<foreach collection='ids' item='id' open='(' separator=',' close=')'>" +
            "#{id}" +
            "</foreach>" +
            "</script>")
    int deleteBatch(@Param("ids") List<Integer> ids);
    
    // 4. 更新课程
    @Update("UPDATE course SET " +
            "course_name = #{courseName}, " +
            "course_number = #{courseNumber}, " +
            "credit = #{credit}, " +
            "teacher = #{teacher}, " +
            "semester = #{semester} " +
            "WHERE id = #{id}")
    int update(Course course);
    
    // 5. 动态更新（只更新非空字段）
    @Update("<script>" +
            "UPDATE course " +
            "<set>" +
            "<if test='courseName != null'>course_name = #{courseName},</if>" +
            "<if test='courseNumber != null'>course_number = #{courseNumber},</if>" +
            "<if test='credit != null'>credit = #{credit},</if>" +
            "<if test='teacher != null'>teacher = #{teacher},</if>" +
            "<if test='semester != null'>semester = #{semester},</if>" +
            "</set>" +
            "WHERE id = #{id}" +
            "</script>")
    int updateSelective(Course course);
    
    // 6. 根据ID查询课程
    @Select("SELECT * FROM course WHERE id = #{id}")
    Course selectById(Integer id);
    
    // 7. 根据课程编号查询（唯一）
    @Select("SELECT * FROM course WHERE course_number = #{courseNumber}")
    Course selectByCourseNumber(String courseNumber);
    
    // 8. 查询所有课程
    @Select("SELECT * FROM course ORDER BY id DESC")
    List<Course> selectAll();
    
    // 9. 条件查询（根据课程名、教师或课程编号）
    @Select("<script>" +
            "SELECT * FROM course WHERE 1=1" +
            "<if test='keyword != null and keyword != \"\"'>" +
            " AND (course_name LIKE CONCAT('%', #{keyword}, '%') " +
            " OR teacher LIKE CONCAT('%', #{keyword}, '%')" +
            " OR course_number LIKE CONCAT('%', #{keyword}, '%'))" +
            "</if>" +
            " ORDER BY id DESC" +
            "</script>")
    List<Course> selectByCondition(@Param("keyword") String keyword);
    
    // 10. 分页查询
    @Select("SELECT * FROM course ORDER BY id DESC LIMIT #{offset}, #{size}")
    List<Course> selectByPage(@Param("offset") int offset, @Param("size") int size);
    
    // 11. 查询总数
    @Select("SELECT COUNT(*) FROM course")
    Long selectCount();
    
    // 12. 条件查询总数
    @Select("<script>" +
            "SELECT COUNT(*) FROM course WHERE 1=1" +
            "<if test='keyword != null and keyword != \"\"'>" +
            " AND (course_name LIKE CONCAT('%', #{keyword}, '%') " +
            " OR teacher LIKE CONCAT('%', #{keyword}, '%')" +
            " OR course_number LIKE CONCAT('%', #{keyword}, '%'))" +
            "</if>" +
            "</script>")
    Long selectCountByCondition(@Param("keyword") String keyword);
    
    // 13. 根据学期查询课程
    @Select("SELECT * FROM course WHERE semester = #{semester} ORDER BY id DESC")
    List<Course> selectBySemester(Integer semester);
    
    // 14. 根据教师查询课程
    @Select("SELECT * FROM course WHERE teacher = #{teacher} ORDER BY id DESC")
    List<Course> selectByTeacher(String teacher);
    
    // 15. 检查课程编号是否已存在
    @Select("SELECT COUNT(*) FROM course WHERE course_number = #{courseNumber}")
    int checkExistByCourseNumber(String courseNumber);
}
