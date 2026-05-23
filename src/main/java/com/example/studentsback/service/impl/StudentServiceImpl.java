package com.example.studentsback.service.impl;

import com.example.studentsback.model.entity.Student;
import com.example.studentsback.service.StudentService;
import org.springframework.stereotype.Service;
import com.example.studentsback.mapper.StudentMapper;

import java.util.List;

/**
 * 学生服务实现
 *
 * 实现 StudentService 接口定义的全部方法，委托给 StudentMapper 执行数据库操作。
 * 涵盖学生增删改查、分页搜索、批量删除和头像更新。
 */
@Service
public class StudentServiceImpl implements StudentService {

    private final StudentMapper studentMapper;

    public StudentServiceImpl(StudentMapper studentMapper) {
        this.studentMapper = studentMapper;
    }

    // 分页条件查询 — 支持按姓名、学号模糊筛选
    @Override
    public List<Student> getStudents(int offset, int pageSize, String name, String studentNumber) {
        return studentMapper.getStudents(offset, pageSize, name, studentNumber);
       }

    // 查询符合条件的学生总数（与分页查询共用同一筛选条件）
    @Override
    public long getTotalCount(String name, String studentNumber) {
        return studentMapper.getTotalCount(name, studentNumber);
    }

    // 根据主键ID查询
    @Override
    public Student getStudentById(int id) {
        return studentMapper.getStudentById(id);
    }

    // 根据学号精确查询（唯一索引字段）
    @Override
    public Student getStudentByStudentNumber(String studentNumber) {
        return studentMapper.getStudentByStudentNumber(studentNumber);
    }

    // 查询全部学生，按 ID 降序
    @Override
    public List<Student> getAllStudents() {
        return studentMapper.getAllStudents();
    }

    // 新增学生 — 主键由数据库自增生成
    @Override
    public void insertStudent(Student student) {
        studentMapper.insertStudent(student);
    }

    // 更新学生全部字段
    @Override
    public void updateStudent(Student student) {
        studentMapper.updateStudent(student);
    }

    // 根据主键删除
    @Override
    public void deleteStudent(int id) {
        studentMapper.deleteStudent(id);
    }

    // 批量删除 — 使用 IN 子句，接收 ID 列表
    @Override
    public void deleteBatchStudents(List<Integer> ids) {
        studentMapper.deleteBatchStudents(ids);
    }

    // 更新头像路径（仅更新 avatar 字段）
    @Override
    public void updateAvatar(Integer id, String avatar) {
        studentMapper.updateAvatar(id, avatar);
    }
}
