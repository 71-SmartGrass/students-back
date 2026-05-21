package com.example.studentsback.exception;

import com.example.studentsback.model.Result;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler {
    //处理学生不存在异常
    @ExceptionHandler(Exception.class)
    public Result<Void> handleException(Exception ex){
        return Result.error(ex.getMessage());
    }
}
