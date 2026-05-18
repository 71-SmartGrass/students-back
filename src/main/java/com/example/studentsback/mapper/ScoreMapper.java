package com.example.studentsback.mapper;

import com.example.studentsback.model.entity.Score;
import org.apache.ibatis.annotations.*;
import java.util.List;
import java.util.Map;

@Mapper
public interface ScoreMapper {
    
    // 插入成绩
    @Insert("INSERT INTO score(student_id, course_id, score) VALUES(#{studentId}, #{courseId}, #{score})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Score score);
    
    // 删除成绩
    @Delete("DELETE FROM score WHERE id = #{id}")
    int deleteById(Integer id);
    
    // 删除学生的所有成绩
    @Delete("DELETE FROM score WHERE student_id = #{studentId}")
    int deleteByStudentId(Integer studentId);
    
    // 删除课程的所有成绩
    @Delete("DELETE FROM score WHERE course_id = #{courseId}")
    int deleteByCourseId(Integer courseId);
    
    // 更新成绩
    @Update("UPDATE score SET score = #{score} WHERE id = #{id}")
    int update(Score score);
    
    // 根据ID查询
    @Select("SELECT * FROM score WHERE id = #{id}")
    Score selectById(Integer id);
    
    // 根据学生ID查询成绩（带学生和课程信息）
    @Select("SELECT s.*, stu.stu_name as studentName, stu.stu_number as studentNumber, " +
            "c.course_name as courseName " +
            "FROM score s " +
            "LEFT JOIN student stu ON s.student_id = stu.id " +
            "LEFT JOIN course c ON s.course_id = c.id " +
            "WHERE s.student_id = #{studentId}")
    List<Score> selectByStudentId(Integer studentId);
    
    // 根据课程ID查询成绩
    @Select("SELECT s.*, stu.stu_name as studentName, stu.stu_number as studentNumber " +
            "FROM score s " +
            "LEFT JOIN student stu ON s.student_id = stu.id " +
            "WHERE s.course_id = #{courseId}")
    List<Score> selectByCourseId(Integer courseId);
    
    // 查询某学生某课程的成绩
    @Select("SELECT * FROM score WHERE student_id = #{studentId} AND course_id = #{courseId}")
    Score selectByStudentAndCourse(@Param("studentId") Integer studentId, @Param("courseId") Integer courseId);
    
    // 统计课程平均分
    @Select("SELECT AVG(score) FROM score WHERE course_id = #{courseId}")
    Double selectAvgScoreByCourseId(Integer courseId);
    
    // 统计学生平均分
    @Select("SELECT AVG(score) FROM score WHERE student_id = #{studentId}")
    Double selectAvgScoreByStudentId(Integer studentId);
    
    // 统计课程及格率
    @Select("SELECT COUNT(CASE WHEN score >= 60 THEN 1 END) * 100.0 / COUNT(*) " +
            "FROM score WHERE course_id = #{courseId}")
    Double selectPassRateByCourseId(Integer courseId);
    
    // 成绩分布统计（优、良、中、及格、不及格）
    @Select("SELECT " +
            "COUNT(CASE WHEN score >= 90 THEN 1 END) as excellent, " +
            "COUNT(CASE WHEN score >= 80 AND score < 90 THEN 1 END) as good, " +
            "COUNT(CASE WHEN score >= 70 AND score < 80 THEN 1 END) as medium, " +
            "COUNT(CASE WHEN score >= 60 AND score < 70 THEN 1 END) as pass, " +
            "COUNT(CASE WHEN score < 60 THEN 1 END) as fail " +
            "FROM score WHERE course_id = #{courseId}")
    Map<String, Integer> selectScoreDistribution(Integer courseId);
}
