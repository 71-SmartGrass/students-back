package com.example.studentsback.service;

import com.example.studentsback.model.entity.Score;
import java.util.List;
import java.util.Map;

public interface ScoreService {

    // 基础 CRUD
    int insert(Score score);
    int deleteById(Integer id);
    int deleteByStudentId(Integer studentId);
    int deleteByCourseId(Integer courseId);
    int update(Score score);
    Score selectById(Integer id);

    // 查询
    List<Score> selectByStudentId(Integer studentId);
    List<Score> selectByCourseId(Integer courseId);
    List<Score> selectByCondition(Integer studentId, Integer courseId);
    Score selectByStudentAndCourse(Integer studentId, Integer courseId);
    Score selectFullByStudentAndCourse(Integer studentId, Integer courseId);

    // 统计方法
    Double selectAvgScoreByCourseId(Integer courseId);
    Double selectAvgScoreByStudentId(Integer studentId);
    Double selectMaxScoreByCourseId(Integer courseId);
    Double selectMinScoreByCourseId(Integer courseId);
    Double selectPassRateByCourseId(Integer courseId);
    Double selectExcellentRateByCourseId(Integer courseId);
    Map<String,Object> selectScoreDistribution(Integer courseId);
    Map<String,Object> selectStudentScoreStatistics(Integer studentId);

    // 检查是否存在
    boolean checkExist(Integer studentId, Integer courseId);
}
