package com.example.studentsback.service.impl;

import com.example.studentsback.mapper.CourseMapper;
import com.example.studentsback.model.entity.Course;
import com.example.studentsback.service.CourseService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 课程服务实现
 *
 * 实现 CourseService 接口定义的全部方法，委托给 CourseMapper 执行数据库操作。
 * 部分查询方法对空值做了防御处理（如 selectBySemester 传入 null 时返回全部课程）。
 */
@Service
public class CourseServiceImpl implements CourseService {

    private final CourseMapper courseMapper;

    public CourseServiceImpl(CourseMapper courseMapper) {
        this.courseMapper = courseMapper;
    }

    // 新增课程
    @Override
    public int insert(Course course) {
        return courseMapper.insert(course);
    }

    // 根据ID删除
    @Override
    public int deleteById(Integer id) {
        return courseMapper.deleteById(id);
    }

    // 批量删除
    @Override
    public int deleteBatch(List<Integer> ids) {
        return courseMapper.deleteBatch(ids);
    }

    // 更新课程
    @Override
    public int update(Course course) {
        return courseMapper.update(course);
    }

    // 根据ID查询
    @Override
    public Course selectById(Integer id) {
        return courseMapper.selectById(id);
    }

    // 根据课程编号查询（唯一索引字段）
    @Override
    public Course selectByCourseNumber(String courseNumber) {
        return courseMapper.selectByCourseNumber(courseNumber);
    }

    // 查询全部课程
    @Override
    public List<Course> selectAll() {
        return courseMapper.selectAll();
    }

    // 条件查询 — 按课程名、教师或课程编号模糊匹配
    @Override
    public List<Course> selectByCondition(String keyword) {
        return courseMapper.selectByCondition(keyword);
    }

    // 分页查询
    @Override
    public List<Course> selectByPage(int offset, int size) {
        return courseMapper.selectByPage(offset, size);
    }

    // 查询总数
    @Override
    public Long selectCount() {
        return courseMapper.selectCount();
    }

    // 根据学期查询（如 2024 秋季学期 = 20241）
    @Override
    public List<Course> selectBySemester(Integer semester) {
        return courseMapper.selectBySemester(semester);
    }

    // 根据授课教师精确查询
    @Override
    public List<Course> selectByTeacher(String teacher) {
        return courseMapper.selectByTeacher(teacher);
    }

    // 检查课程编号是否已存在（用于新增时的唯一性校验）
    @Override
    public boolean checkExistByCourseNumber(String courseNumber) {
        return courseMapper.checkExistByCourseNumber(courseNumber) > 0;
    }
}
