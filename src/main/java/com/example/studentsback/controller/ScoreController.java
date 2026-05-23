package com.example.studentsback.controller;

import com.example.studentsback.common.Result;
import com.example.studentsback.model.entity.Score;
import com.example.studentsback.service.ScoreService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/scores")
public class ScoreController {

    private final ScoreService scoreService;

    public ScoreController(ScoreService scoreService) {
        this.scoreService = scoreService;
    }

    // ========== 基础 CRUD ==========

    @PostMapping
    public Result<Integer> add(@RequestBody Score score) {
        int result = scoreService.insert(score);
        return Result.success(result);
    }

    @DeleteMapping("/{id}")
    public Result<Integer> deleteById(@PathVariable Integer id) {
        int result = scoreService.deleteById(id);
        return Result.success(result);
    }

    @DeleteMapping("/student/{studentId}")
    public Result<Integer> deleteByStudentId(@PathVariable Integer studentId) {
        int result = scoreService.deleteByStudentId(studentId);
        return Result.success(result);
    }

    @DeleteMapping("/course/{courseId}")
    public Result<Integer> deleteByCourseId(@PathVariable Integer courseId) {
        int result = scoreService.deleteByCourseId(courseId);
        return Result.success(result);
    }

    @PutMapping
    public Result<Integer> update(@RequestBody Score score) {
        int result = scoreService.update(score);
        return Result.success(result);
    }

    @GetMapping("/{id}")
    public Result<Score> getById(@PathVariable Integer id) {
        Score score = scoreService.selectById(id);
        return Result.success(score);
    }

    // ========== 查询 ==========

    @GetMapping("/student/{studentId}")
    public Result<List<Score>> getByStudentId(@PathVariable Integer studentId) {
        List<Score> list = scoreService.selectByStudentId(studentId);
        return Result.success(list);
    }

    @GetMapping("/course/{courseId}")
    public Result<List<Score>> getByCourseId(@PathVariable Integer courseId) {
        List<Score> list = scoreService.selectByCourseId(courseId);
        return Result.success(list);
    }

    @GetMapping("/check")
    public Result<Boolean> checkExist(@RequestParam Integer studentId, @RequestParam Integer courseId) {
        boolean exist = scoreService.checkExist(studentId, courseId);
        return Result.success(exist);
    }

    @GetMapping("/full")
    public Result<Score> getFull(@RequestParam Integer studentId, @RequestParam Integer courseId) {
        Score score = scoreService.selectFullByStudentAndCourse(studentId, courseId);
        return Result.success(score);
    }

    // ========== 统计 ==========

    @GetMapping("/course/{courseId}/avg")
    public Result<Double> getCourseAvg(@PathVariable Integer courseId) {
        Double avg = scoreService.selectAvgScoreByCourseId(courseId);
        return Result.success(avg);
    }

    @GetMapping("/student/{studentId}/avg")
    public Result<Double> getStudentAvg(@PathVariable Integer studentId) {
        Double avg = scoreService.selectAvgScoreByStudentId(studentId);
        return Result.success(avg);
    }

    @GetMapping("/course/{courseId}/max")
    public Result<Double> getCourseMax(@PathVariable Integer courseId) {
        Double max = scoreService.selectMaxScoreByCourseId(courseId);
        return Result.success(max);
    }

    @GetMapping("/course/{courseId}/min")
    public Result<Double> getCourseMin(@PathVariable Integer courseId) {
        Double min = scoreService.selectMinScoreByCourseId(courseId);
        return Result.success(min);
    }

    @GetMapping("/course/{courseId}/passRate")
    public Result<Double> getPassRate(@PathVariable Integer courseId) {
        Double rate = scoreService.selectPassRateByCourseId(courseId);
        return Result.success(rate);
    }

    @GetMapping("/course/{courseId}/excellentRate")
    public Result<Double> getExcellentRate(@PathVariable Integer courseId) {
        Double rate = scoreService.selectExcellentRateByCourseId(courseId);
        return Result.success(rate);
    }

    @GetMapping("/course/{courseId}/distribution")
    public Result<Map<String, Object>> getDistribution(@PathVariable Integer courseId) {
        Map<String, Object> data = scoreService.selectScoreDistribution(courseId);
        return Result.success(data);
    }

    @GetMapping("/student/{studentId}/statistics")
    public Result<Map<String, Object>> getStudentStatistics(@PathVariable Integer studentId) {
        Map<String, Object> data = scoreService.selectStudentScoreStatistics(studentId);
        return Result.success(data);
    }
}