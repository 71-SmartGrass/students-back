package com.example.studentsback.service.impl;

import com.example.studentsback.model.entity.Student;
import com.example.studentsback.service.StudentService;
import org.springframework.stereotype.Service;
import com.example.studentsback.mapper.StudentMapper;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentMapper studentMapper;

    public StudentServiceImpl(StudentMapper studentMapper) {
        this.studentMapper = studentMapper;
    }

    @Override
    public List<Student> getStudents(int offset, int pageSize, String name, String studentNumber) {
        return studentMapper.getStudents(offset, pageSize, name, studentNumber);
       }

    @Override
    public long getTotalCount(String name, String studentNumber) {
        return studentMapper.getTotalCount(name, studentNumber);
    }

    @Override
    public Student getStudentById(int id) {
        return studentMapper.getStudentById(id);
    }

    @Override
    public Student getStudentByStudentNumber(String studentNumber) {
        return studentMapper.getStudentByStudentNumber(studentNumber);
    }

    @Override
    public List<Student> getAllStudents() {
        return studentMapper.getAllStudents();
    }

    @Override
    public void insertStudent(Student student) {
        studentMapper.insertStudent(student);
    }

    @Override
    public void updateStudent(Student student) {
        studentMapper.updateStudent(student);
    }

    @Override
    public void deleteStudent(int id) {
        studentMapper.deleteStudent(id);
    }

    @Override
    public void deleteBatchStudents(List<Integer> ids) {
        studentMapper.deleteBatchStudents(ids);
    }
}
