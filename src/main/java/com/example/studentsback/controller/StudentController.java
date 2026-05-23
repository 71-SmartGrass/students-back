package com.example.studentsback.controller;

import com.example.studentsback.common.Result;
import com.example.studentsback.model.entity.Student;
import com.example.studentsback.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

/**
 * 学生管理控制器
 *
 * 提供学生增删改查 REST API，路径前缀为 /api/students。
 * 支持分页查询、按姓名/学号搜索、批量删除。
 * 额外提供头像上传接口，文件通过 FormData 提交。
 */
@RestController
@RequestMapping("/api/students")
public class StudentController {

    private final StudentService studentService;

    @Value("${file.upload.path}")
    private String uploadPath;

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
    public Result<Student> add(@Valid @RequestBody Student student){
        studentService.insertStudent(student);
        return Result.success();
    }

    //更新学生
    @PutMapping("/{id}")
    public Result<Student> update(@PathVariable int id, @Valid @RequestBody Student student){
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

    //上传头像
    @PostMapping("/{id}/avatar")
    public Result<String> uploadAvatar(@PathVariable Integer id,
                                       @RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return Result.error(400, "文件不能为空");
        }

        String originalFilename = file.getOriginalFilename();
        String suffix = "";
        if (originalFilename != null && originalFilename.contains(".")) {
            suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        }
        String filename = UUID.randomUUID().toString() + suffix;

        File dest = new File(uploadPath + File.separator + filename);
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
        try {
            file.transferTo(dest);
        } catch (IOException e) {
            return Result.error(500, "文件上传失败");
        }

        String avatarUrl = "/uploads/" + filename;
        studentService.updateAvatar(id, avatarUrl);
        return Result.success(avatarUrl);
    }
}
