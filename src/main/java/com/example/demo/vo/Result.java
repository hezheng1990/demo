package com.example.demo.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Result <T>{

    private Integer code;

    private String msg;

    private T data;

    public Result(T data) {
        this.data = data;
    }

    public static <T> Result success(T t){
        Result<T> result = new Result<>(t);
        result.setCode(1);
        result.setMsg("请求成功");
        return result;
    }

    public static <T> Result failed(String msg){
        Result<T> result = new Result<>();
        result.setCode(-1);
        result.setMsg(msg);
        return result;
    }

}
