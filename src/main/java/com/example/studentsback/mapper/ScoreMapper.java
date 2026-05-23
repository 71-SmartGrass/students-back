package com.example.studentsback.mapper;

import com.example.studentsback.model.entity.Score;
import org.apache.ibatis.annotations.*;
import java.util.List;
import java.util.Map;

@Mapper
public interface ScoreMapper {

    //插入成绩
    @Insert("INSERT INTO score(student_id, course_id, score) VALUES(#{studentId}, #{courseId}, #{score})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Score score);

    //根据ID删除成绩
    @Delete("DELETE FROM score WHERE id = #{id}")
    int deleteById(Integer id);

    //删除学生的所有成绩
    @Delete("DELETE FROM score WHERE student_id = #{studentId}")
    int deleteByStudentId(Integer studentId);

    //删除课程的所有成绩
    @Delete("DELETE FROM score WHERE course_id = #{courseId}")
    int deleteByCourseId(Integer courseId);

    //更新成绩
    @Update("UPDATE score SET score = #{score} WHERE id = #{id}")
    int update(Score score);

    //根据ID查询成绩
    @Select("SELECT * FROM score WHERE id = #{id}")
    Score selectById(Integer id);

    //根据学生ID查询成绩（关联学生和课程信息）
    @Select("SELECT " +
            "s.id, s.student_id, s.course_id, s.score, s.create_time as createTime, " +
            "stu.name as studentName, " +
            "stu.student_number as studentNumber, " +
            "c.course_name as courseName, " +
            "c.course_number as courseNumber, " +
            "c.credit as credit, " +
            "c.teacher as teacher, " +
            "c.semester as semester " +
            "FROM score s " +
            "LEFT JOIN student stu ON s.student_id = stu.id " +
            "LEFT JOIN course c ON s.course_id = c.id " +
            "WHERE s.student_id = #{studentId}")
    List<Score> selectByStudentId(Integer studentId);

    // 组合条件查询成绩（studentId 和 courseId 均可选）
    @Select("<script>" +
            "SELECT " +
            "s.id, s.student_id, s.course_id, s.score, s.create_time as createTime, " +
            "stu.name as studentName, " +
            "stu.student_number as studentNumber, " +
            "c.course_name as courseName, " +
            "c.course_number as courseNumber, " +
            "c.credit as credit, " +
            "c.teacher as teacher, " +
            "c.semester as semester " +
            "FROM score s " +
            "LEFT JOIN student stu ON s.student_id = stu.id " +
            "LEFT JOIN course c ON s.course_id = c.id " +
            "WHERE 1=1" +
            "<if test='studentId != null'> AND s.student_id = #{studentId}</if>" +
            "<if test='courseId != null'> AND s.course_id = #{courseId}</if>" +
            " ORDER BY s.id DESC" +
            "</script>")
    List<Score> selectByCondition(@Param("studentId") Integer studentId,
                                  @Param("courseId") Integer courseId);

    //根据课程ID查询成绩（关联学生信息）
    @Select("SELECT " +
            "s.id, s.student_id, s.course_id, s.score, s.create_time as createTime, " +
            "stu.name as studentName, " +
            "stu.student_number as studentNumber, " +
            "c.course_name as courseName, " +
            "c.course_number as courseNumber, " +
            "c.credit as credit, " +
            "c.teacher as teacher, " +
            "c.semester as semester " +
            "FROM score s " +
            "LEFT JOIN student stu ON s.student_id = stu.id " +
            "LEFT JOIN course c ON s.course_id = c.id " +
            "WHERE s.course_id = #{courseId}")
    List<Score> selectByCourseId(Integer courseId);

    //查询某学生某课程的成绩（基础信息）
    @Select("SELECT * FROM score WHERE student_id = #{studentId} AND course_id = #{courseId}")
    Score selectByStudentAndCourse(@Param("studentId") Integer studentId, @Param("courseId") Integer courseId);

    //查询某学生某课程的成绩（带完整信息）
    @Select("SELECT " +
            "s.id, s.student_id, s.course_id, s.score, s.create_time as createTime, " +
            "stu.name as studentName, " +
            "stu.student_number as studentNumber, " +
            "c.course_name as courseName, " +
            "c.course_number as courseNumber, " +
            "c.credit as credit, " +
            "c.teacher as teacher, " +
            "c.semester as semester " +
            "FROM score s " +
            "LEFT JOIN student stu ON s.student_id = stu.id " +
            "LEFT JOIN course c ON s.course_id = c.id " +
            "WHERE s.student_id = #{studentId} AND s.course_id = #{courseId}")
    Score selectFullByStudentAndCourse(@Param("studentId") Integer studentId, @Param("courseId") Integer courseId);

    //根据课程ID查询平均分
    @Select("SELECT AVG(score) FROM score WHERE course_id = #{courseId}")
    Double selectAvgScoreByCourseId(Integer courseId);

    //统计学生平均分
    @Select("SELECT AVG(score) FROM score WHERE student_id = #{studentId}")
    Double selectAvgScoreByStudentId(Integer studentId);

    //统计课程最高分
    @Select("SELECT MAX(score) FROM score WHERE course_id = #{courseId}")
    Double selectMaxScoreByCourseId(Integer courseId);

    //统计课程最低分
    @Select("SELECT MIN(score) FROM score WHERE course_id = #{courseId}")
    Double selectMinScoreByCourseId(Integer courseId);

    //根据课程ID查询及格率
    @Select("SELECT COUNT(CASE WHEN score >= 60 THEN 1 END) * 100.0 / COUNT(*) " +
            "FROM score WHERE course_id = #{courseId}")
    Double selectPassRateByCourseId(Integer courseId);

    //统计课程优秀率（>=80）
    @Select("SELECT COUNT(CASE WHEN score >= 80 THEN 1 END) * 100.0 / COUNT(*) " +
            "FROM score WHERE course_id = #{courseId}")
    Double selectExcellentRateByCourseId(Integer courseId);

    //成绩分布统计
    @Select("SELECT " +
            "COUNT(CASE WHEN score >= 90 THEN 1 END) as excellent, " +
            "COUNT(CASE WHEN score >= 80 AND score < 90 THEN 1 END) as good, " +
            "COUNT(CASE WHEN score >= 70 AND score < 80 THEN 1 END) as medium, " +
            "COUNT(CASE WHEN score >= 60 AND score < 70 THEN 1 END) as pass, " +
            "COUNT(CASE WHEN score < 60 THEN 1 END) as fail " +
            "FROM score WHERE course_id = #{courseId}")
    Map<String, Object> selectScoreDistribution(Integer courseId);

    //查询学生的所有成绩统计
    @Select("SELECT " +
            "COUNT(*) as courseCount, " +
            "SUM(score) as totalScore, " +
            "AVG(score) as avgScore, " +
            "MAX(score) as maxScore, " +
            "MIN(score) as minScore " +
            "FROM score WHERE student_id = #{studentId}")
    Map<String, Object> selectStudentScoreStatistics(Integer studentId);

    //检查成绩是否已存在
    @Select("SELECT COUNT(*) FROM score WHERE student_id = #{studentId} AND course_id = #{courseId}")
    int checkExist(@Param("studentId") Integer studentId, @Param("courseId") Integer courseId);
}
