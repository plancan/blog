package com.domain;

import com.enums.HttpCodeEnum;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * @author 今昔
 * @description 统一响应类
 * @date 2022/11/9 15:14
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Resp<T> {
    private int code;
    private String message;
    private T data;
    /**
    *@param  * @param code
     * @param message
     * @param data
    *@return {@link null}
    *@description 全参构造
    **/
    public Resp(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }
    /**
    *@param  * @param data
    *@return {@link null}
    *@description 响应成功的构造
    **/
    public Resp(T data){
        this.code=HttpCodeEnum.SUCCESS.getCode();
        this.message=HttpCodeEnum.SUCCESS.getMessage();
        this.data=data;
    }
    /**
    *@param  * @param httpCodeEnum
    *@return {@link null}
    *@description 响应失败的构造
    **/
    public Resp(HttpCodeEnum httpCodeEnum){
        this.message=httpCodeEnum.getMessage();
        this.code=httpCodeEnum.getCode();
        this.data=null;
    }
    
    public Resp() {
        this.code=HttpCodeEnum.SUCCESS.getCode();
        this.message=HttpCodeEnum.SUCCESS.getMessage();
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
