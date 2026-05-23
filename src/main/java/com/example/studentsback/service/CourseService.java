package com.example.studentsback.service;

import com.example.studentsback.model.entity.Course;
import java.util.List;

public interface CourseService {

    // 新增课程
    int insert(Course course);

    // 根据ID删除
    int deleteById(Integer id);

    // 批量删除
    int deleteBatch(List<Integer> ids);

    // 更新课程
    int update(Course course);

    // 根据ID查询
    Course selectById(Integer id);

    // 根据课程编号查询
    Course selectByCourseNumber(String courseNumber);

    // 查询所有
    List<Course> selectAll();

    // 条件查询
    List<Course> selectByCondition(String keyword);

    // 分页查询
    List<Course> selectByPage(int offset, int size);

    // 查询总数
    Long selectCount();

    // 根据学期查询
    List<Course> selectBySemester(Integer semester);

    // 根据教师查询
    List<Course> selectByTeacher(String teacher);

    // 检查课程编号是否存在
    boolean checkExistByCourseNumber(String courseNumber);
}
