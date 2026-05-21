package com.example.studentsback.model;

public class Result<T> {
    /*
    状态码
    200 成功
    500 数据库错误
    400 请求参数错误
     */
    private Integer code; // 状态码
    private String msg; // 提示信息 
    private T data; // 数据

    private Result(Integer code, String msg, T data) {
        this.code = code;
        this.data = data;
        this.msg = msg;
    }
    
    //success && data != null
    public static <T> Result<T> success(T data) {
        return new Result<>(200, "success", data);
    }
    
    //success && data == null 
    public static <T> Result<T> success() {
        return new Result<>(200, "success", null);
    }

    //fail
    public static <T> Result<T> fail() {
        return new Result<>(500, "fail", null);
    }
    
    //error
    public static <T> Result<T> error(Integer code, String message) {
        return new Result<>(code, message, null);
    }

    //getters
    public Integer getCode() {return code;}
    public String getMsg() {return msg;}
    public T getData() {return data;}
}   
