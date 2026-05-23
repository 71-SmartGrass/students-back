package com.example.studentsback.exception;

import com.example.studentsback.common.Result;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器
 *
 * 拦截 Controller 层所有未被捕获的异常，统一返回 Result 格式的错误响应。
 * 避免异常信息直接暴露给前端，同时保证返回格式一致。
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    // 入参校验失败 — 配合 @Valid 注解，提取第一条字段错误信息返回
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result<Void> handleValidation(MethodArgumentNotValidException ex) {
        String msg = ex.getBindingResult().getFieldError().getDefaultMessage();
        return Result.error(400, msg);
    }

    // 兜底 — 处理所有未预料的异常，返回 HTTP 500
    @ExceptionHandler(Exception.class)
    public Result<Void> handleException(Exception ex) {
        return Result.error(500, ex.getMessage());
    }
}
