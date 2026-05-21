package com.example.studentsback.model;

public class Result<T> {
    private Long code; // 状态码 1->成功 0->失败
    private String msg; // 提示信息 
    private T data; // 数据

    private Result(Long code, String msg, T data) {
        this.code = code;
        this.data = data;
        this.msg = msg;
    }
    
    //success && data != null
    public static <T> Result<T> success(T data) {
        return new Result<>(1L, "success", data);
    }
    
    //success && data == null 
    public static <T> Result<T> success() {
        return new Result<>(1L, "success", null);
    }

    //fail
    public static <T> Result<T> fail() {
        return new Result<>(0L, "fail", null);
    }
    
    //error
    public static <T> Result<T> error(String message) {
        return new Result<>(0L, message, null);
    }

    //getters
    public Long getCode() {return code;}
    public String getMsg() {return msg;}
    public T getData() {return data;}
}   
