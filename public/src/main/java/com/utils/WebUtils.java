package com.utils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author 今昔
 * @Description 返回信息类
 */
public class WebUtils {
    public static String renderString(HttpServletResponse response,String string){
        response.setStatus(200);
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        try {
            response.getWriter().print(string);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
