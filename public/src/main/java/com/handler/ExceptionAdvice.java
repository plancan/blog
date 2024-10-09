package com.handler;


import com.domain.Resp;
import com.exception.SystemException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author 今昔
 * @Description 异常处理器
 */
@ControllerAdvice
@RestControllerAdvice
public class ExceptionAdvice {
    //处理自定义异常
    @ExceptionHandler
    public Resp systemExceptionHandler(SystemException systemException) {
        Resp resp = new Resp();
        resp.setData(null);
        //将错误信息返回
        resp.setMessage(systemException.getMessage());
        resp.setCode(systemException.getCode());
        systemException.printStackTrace();
        return resp;
    }
    //处理其他异常
    @ExceptionHandler
    public Resp exceptionHandler(Exception e) {
        Resp resp = new Resp();
        resp.setMessage(e.getMessage());
        resp.setCode(500);
        //将错误信息返回
        e.printStackTrace();
        return resp;
    }
}
