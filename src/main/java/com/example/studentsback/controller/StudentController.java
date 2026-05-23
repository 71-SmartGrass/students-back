package com.example.studentsback.controller;

import com.example.studentsback.model.Result;
import com.example.studentsback.model.entity.Student;
import com.example.studentsback.service.StudentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    // 分页查询学生列表
    @GetMapping
    public Result<List<Student>> list(
        @RequestParam(defaultValue = "1") int page,
        @RequestParam(defaultValue = "10") int pageSize,
        @RequestParam(required = false) String name,
        @RequestParam(required = false) String studentNumber
    ){
        int offset = (page - 1) * pageSize; // 偏移量 从0开始
        List<Student> students = studentService.getStudents(offset, pageSize, name, studentNumber);
        return Result.success(students);
    }

    // count 查询学生总数
    @GetMapping("/count")
    public Result<Long> count(
        @RequestParam(required = false) String name,
        @RequestParam(required = false) String studentNumber
    ){
        long count = studentService.getTotalCount(name, studentNumber);
        return Result.success(count);
    }

    // 根据ID获取学生
    @GetMapping("/{id}")
    public Result<Student> getById(@PathVariable int id){
        Student student = studentService.getStudentById(id);
        return Result.success(student);
    }

    //添加学生
    @PostMapping
    public Result<Student> add(@RequestBody Student student){
        studentService.insertStudent(student);
        return Result.success();
    }

    //更新学生
    @PutMapping("/{id}")
    public Result<Student> update(@PathVariable int id, @RequestBody Student student){
        studentService.updateStudent(student);
        return Result.success();
    }

    //删除学生
    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable int id){
        studentService.deleteStudent(id);
        return Result.success();
    }

    //批量删除学生
    @DeleteMapping("/batch")
    public Result<Void> batchDelete(@RequestBody List<Integer> ids){
        studentService.deleteBatchStudents(ids);
        return Result.success();
    }
}
