package com.example.studentsback.service.impl;

import com.example.studentsback.mapper.CourseMapper;
import com.example.studentsback.model.entity.Course;
import com.example.studentsback.service.CourseService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {

    private final CourseMapper courseMapper;

    public CourseServiceImpl(CourseMapper courseMapper) {
        this.courseMapper = courseMapper;
    }

    @Override
    public int insert(Course course) {
        return courseMapper.insert(course);
    }

    @Override
    public int deleteById(Integer id) {
        return courseMapper.deleteById(id);
    }

    @Override
    public int deleteBatch(List<Integer> ids) {
        return courseMapper.deleteBatch(ids);
    }

    @Override
    public int update(Course course) {
        return courseMapper.update(course);
    }

    @Override
    public Course selectById(Integer id) {
        return courseMapper.selectById(id);
    }

    @Override
    public Course selectByCourseNumber(String courseNumber) {
        return courseMapper.selectByCourseNumber(courseNumber);
    }

    @Override
    public List<Course> selectAll() {
        return courseMapper.selectAll();
    }

    @Override
    public List<Course> selectByCondition(String keyword) {
        return courseMapper.selectByCondition(keyword);
    }

    @Override
    public List<Course> selectByPage(int offset, int size) {
        return courseMapper.selectByPage(offset, size);
    }

    @Override
    public Long selectCount() {
        return courseMapper.selectCount();
    }

    @Override
    public List<Course> selectBySemester(Integer semester) {
        return courseMapper.selectBySemester(semester);
    }

    @Override
    public List<Course> selectByTeacher(String teacher) {
        return courseMapper.selectByTeacher(teacher);
    }

    @Override
    public boolean checkExistByCourseNumber(String courseNumber) {
        return courseMapper.checkExistByCourseNumber(courseNumber) > 0;
    }
}