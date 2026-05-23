package com.example.studentsback.controller;

import com.example.studentsback.common.Result;
import com.example.studentsback.model.entity.Course;
import com.example.studentsback.service.CourseService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    // 新增课程
    @PostMapping
    public Result<Integer> add(@RequestBody Course course) {
        int result = courseService.insert(course);
        return Result.success(result);
    }

    // 根据ID删除
    @DeleteMapping("/{id}")
    public Result<Integer> deleteById(@PathVariable Integer id) {
        int result = courseService.deleteById(id);
        return Result.success(result);
    }

    // 批量删除
    @DeleteMapping("/batch")
    public Result<Integer> deleteBatch(@RequestBody List<Integer> ids) {
        int result = courseService.deleteBatch(ids);
        return Result.success(result);
    }

    // 更新课程
    @PutMapping
    public Result<Integer> update(@RequestBody Course course) {
        int result = courseService.update(course);
        return Result.success(result);
    }

    // 根据ID查询
    @GetMapping("/{id}")
    public Result<Course> getById(@PathVariable Integer id) {
        Course course = courseService.selectById(id);
        return Result.success(course);
    }

    // 根据课程编号查询
    @GetMapping("/number/{courseNumber}")
    public Result<Course> getByNumber(@PathVariable String courseNumber) {
        Course course = courseService.selectByCourseNumber(courseNumber);
        return Result.success(course);
    }

    // 查询所有
    @GetMapping("/list")
    public Result<List<Course>> list() {
        List<Course> list = courseService.selectAll();
        return Result.success(list);
    }

    // 条件查询
    @GetMapping("/search")
    public Result<List<Course>> search(@RequestParam(required = false) String keyword) {
        List<Course> list = courseService.selectByCondition(keyword);
        return Result.success(list);
    }

    // 分页查询
    @GetMapping("/page")
    public Result<List<Course>> page(@RequestParam(defaultValue = "0") int offset,
                                     @RequestParam(defaultValue = "10") int size) {
        List<Course> list = courseService.selectByPage(offset, size);
        return Result.success(list);
    }

    // 查询总数
    @GetMapping("/count")
    public Result<Long> count() {
        Long count = courseService.selectCount();
        return Result.success(count);
    }

    // 根据学期查询
    @GetMapping("/semester/{semester}")
    public Result<List<Course>> getBySemester(@PathVariable Integer semester) {
        List<Course> list = courseService.selectBySemester(semester);
        return Result.success(list);
    }

    // 根据教师查询
    @GetMapping("/teacher/{teacher}")
    public Result<List<Course>> getByTeacher(@PathVariable String teacher) {
        List<Course> list = courseService.selectByTeacher(teacher);
        return Result.success(list);
    }
}