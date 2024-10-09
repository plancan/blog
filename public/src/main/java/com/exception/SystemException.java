package com.exception;

import com.enums.HttpCodeEnum;

/**
 * @author 今昔
 * @description 自定义异常类
 * @date 2022/11/11 20:35
 */
public class SystemException extends RuntimeException {
    int code;
    String message;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public SystemException(HttpCodeEnum httpCodeEnum){
        this.code=httpCodeEnum.getCode();
        this.message=httpCodeEnum.getMessage();
    }
}
