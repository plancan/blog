package com.handler;

import com.alibaba.fastjson.JSON;
import com.domain.Resp;
import com.enums.HttpCodeEnum;
import com.utils.WebUtils;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author 今昔
 * @description 授权失败处理器
 * @date 2022/11/12 17:07
 */
@Component
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        Resp resp = new Resp(HttpCodeEnum.WITHOUT_AUTH0RITY);
        //将该信息返回
        String json = JSON.toJSONString(resp);
        WebUtils.renderString(response, json);
    }
}