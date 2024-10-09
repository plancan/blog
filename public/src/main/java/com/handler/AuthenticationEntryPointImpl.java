package com.handler;

import com.alibaba.fastjson.JSON;
import com.domain.Resp;
import com.enums.HttpCodeEnum;
import com.utils.WebUtils;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author 今昔
 * @description 认证失败处理器
 * @date 2022/11/12 17:09
 */
@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        Resp resp =new  Resp(HttpCodeEnum.WITHOUT_LOGIN);
        //将该信息返回
        String json= JSON.toJSONString(resp);
        WebUtils.renderString(response, json);
    }
}
