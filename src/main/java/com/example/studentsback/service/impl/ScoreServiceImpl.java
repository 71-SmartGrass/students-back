package com.example.studentsback.service.impl;

import com.example.studentsback.mapper.ScoreMapper;
import com.example.studentsback.model.entity.Score;
import com.example.studentsback.service.ScoreService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ScoreServiceImpl implements ScoreService {

    private final ScoreMapper scoreMapper;

    public ScoreServiceImpl(ScoreMapper scoreMapper) {
        this.scoreMapper = scoreMapper;
    }

    // 基础 CRUD
    @Override
    public int insert(Score score) {
        return scoreMapper.insert(score);
    }

    @Override
    public int deleteById(Integer id) {
        return scoreMapper.deleteById(id);
    }

    @Override
    public int deleteByStudentId(Integer studentId) {
        return scoreMapper.deleteByStudentId(studentId);
    }

    @Override
    public int deleteByCourseId(Integer courseId) {
        return scoreMapper.deleteByCourseId(courseId);
    }

    @Override
    public int update(Score score) {
        return scoreMapper.update(score);
    }

    @Override
    public Score selectById(Integer id) {
        return scoreMapper.selectById(id);
    }

    // 查询
    @Override
    public List<Score> selectByStudentId(Integer studentId) {
        return scoreMapper.selectByStudentId(studentId);
    }

    @Override
    public List<Score> selectByCourseId(Integer courseId) {
        return scoreMapper.selectByCourseId(courseId);
    }

    @Override
    public Score selectByStudentAndCourse(Integer studentId, Integer courseId) {
        return scoreMapper.selectByStudentAndCourse(studentId, courseId);
    }

    @Override
    public Score selectFullByStudentAndCourse(Integer studentId, Integer courseId) {
        return scoreMapper.selectFullByStudentAndCourse(studentId, courseId);
    }

    // 统计方法
    @Override
    public Double selectAvgScoreByCourseId(Integer courseId) {
        return scoreMapper.selectAvgScoreByCourseId(courseId);
    }

    @Override
    public Double selectAvgScoreByStudentId(Integer studentId) {
        return scoreMapper.selectAvgScoreByStudentId(studentId);
    }

    @Override
    public Double selectMaxScoreByCourseId(Integer courseId) {
        return scoreMapper.selectMaxScoreByCourseId(courseId);
    }

    @Override
    public Double selectMinScoreByCourseId(Integer courseId) {
        return scoreMapper.selectMinScoreByCourseId(courseId);
    }

    @Override
    public Double selectPassRateByCourseId(Integer courseId) {
        return scoreMapper.selectPassRateByCourseId(courseId);
    }

    @Override
    public Double selectExcellentRateByCourseId(Integer courseId) {
        return scoreMapper.selectExcellentRateByCourseId(courseId);
    }

    @Override
    public Map<String,Object> selectScoreDistribution(Integer courseId) {
        return scoreMapper.selectScoreDistribution(courseId);
    }

    @Override
    public Map<String,Object> selectStudentScoreStatistics(Integer studentId) {
        return scoreMapper.selectStudentScoreStatistics(studentId);
    }

    @Override
    public boolean checkExist(Integer studentId, Integer courseId) {
        return scoreMapper.checkExist(studentId, courseId) > 0;
    }
}