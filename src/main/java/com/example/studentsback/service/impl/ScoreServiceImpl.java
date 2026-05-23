package com.example.studentsback.service.impl;

import com.example.studentsback.mapper.ScoreMapper;
import com.example.studentsback.model.entity.Score;
import com.example.studentsback.service.ScoreService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 成绩服务实现
 *
 * 实现 ScoreService 接口定义的全部方法，委托给 ScoreMapper 执行数据库操作。
 * 方法按功能分组：基础 CRUD、关联查询、统计分析。
 */
@Service
public class ScoreServiceImpl implements ScoreService {

    private final ScoreMapper scoreMapper;

    public ScoreServiceImpl(ScoreMapper scoreMapper) {
        this.scoreMapper = scoreMapper;
    }

    // ========== 基础 CRUD ==========

    // 新增成绩记录
    @Override
    public int insert(Score score) {
        return scoreMapper.insert(score);
    }

    // 根据ID删除
    @Override
    public int deleteById(Integer id) {
        return scoreMapper.deleteById(id);
    }

    // 删除某学生的全部成绩（如学生被删除时级联清理）
    @Override
    public int deleteByStudentId(Integer studentId) {
        return scoreMapper.deleteByStudentId(studentId);
    }

    // 删除某课程的全部成绩（如课程被删除时级联清理）
    @Override
    public int deleteByCourseId(Integer courseId) {
        return scoreMapper.deleteByCourseId(courseId);
    }

    // 更新成绩（只更新分数）
    @Override
    public int update(Score score) {
        return scoreMapper.update(score);
    }

    // 根据ID查询
    @Override
    public Score selectById(Integer id) {
        return scoreMapper.selectById(id);
    }

    // ========== 关联查询 ==========

    // 查询某学生的全部成绩 — 三表 JOIN 带学生和课程信息
    @Override
    public List<Score> selectByStudentId(Integer studentId) {
        return scoreMapper.selectByStudentId(studentId);
    }

    // 查询某课程的全部成绩 — 三表 JOIN 带学生和课程信息
    @Override
    public List<Score> selectByCourseId(Integer courseId) {
        return scoreMapper.selectByCourseId(courseId);
    }

    // 组合条件查询 — studentId 和 courseId 均可选，不传则查全部
    @Override
    public List<Score> selectByCondition(Integer studentId, Integer courseId) {
        return scoreMapper.selectByCondition(studentId, courseId);
    }

    // 查询学生+课程唯一组合的基础成绩（不含 JOIN 信息）
    @Override
    public Score selectByStudentAndCourse(Integer studentId, Integer courseId) {
        return scoreMapper.selectByStudentAndCourse(studentId, courseId);
    }

    // 查询学生+课程完整信息（三表 JOIN，含成绩、学生名、课程名、教师等）
    @Override
    public Score selectFullByStudentAndCourse(Integer studentId, Integer courseId) {
        return scoreMapper.selectFullByStudentAndCourse(studentId, courseId);
    }

    // ========== 统计分析 ==========

    // 课程平均分
    @Override
    public Double selectAvgScoreByCourseId(Integer courseId) {
        return scoreMapper.selectAvgScoreByCourseId(courseId);
    }

    // 学生平均分
    @Override
    public Double selectAvgScoreByStudentId(Integer studentId) {
        return scoreMapper.selectAvgScoreByStudentId(studentId);
    }

    // 课程最高分
    @Override
    public Double selectMaxScoreByCourseId(Integer courseId) {
        return scoreMapper.selectMaxScoreByCourseId(courseId);
    }

    // 课程最低分
    @Override
    public Double selectMinScoreByCourseId(Integer courseId) {
        return scoreMapper.selectMinScoreByCourseId(courseId);
    }

    // 课程及格率（>= 60 分的比例，单位 %）
    @Override
    public Double selectPassRateByCourseId(Integer courseId) {
        return scoreMapper.selectPassRateByCourseId(courseId);
    }

    // 课程优秀率（>= 80 分的比例，单位 %）
    @Override
    public Double selectExcellentRateByCourseId(Integer courseId) {
        return scoreMapper.selectExcellentRateByCourseId(courseId);
    }

    // 成绩分布统计 — 返回各分数段人数（优秀 良好 中等 及格 不及格）
    @Override
    public Map<String, Object> selectScoreDistribution(Integer courseId) {
        return scoreMapper.selectScoreDistribution(courseId);
    }

    // 学生成绩汇总 — 总课程数、总分、均分、最高、最低
    @Override
    public Map<String, Object> selectStudentScoreStatistics(Integer studentId) {
        return scoreMapper.selectStudentScoreStatistics(studentId);
    }

    // 检查成绩记录是否已存在（学生+课程唯一约束校验）
    @Override
    public boolean checkExist(Integer studentId, Integer courseId) {
        return scoreMapper.checkExist(studentId, courseId) > 0;
    }
}
