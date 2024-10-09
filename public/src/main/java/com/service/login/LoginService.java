package com.service.login;

import com.domain.User;
import com.domain.UserVo;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author 今昔
 * @description 登录相关服务
 * @date 2022/11/11 21:50
 */
public interface LoginService {
    public void valid(String token);
    /**
    *@param  * @param key
    *@return {@link Map}
    *@description 使用id或邮箱号登录
    **/
    public UserVo login(User user);
    /**
    *@param  * @param
    *@return
    *@description 退出登录接口
    **/
    public void logout();
    /**
    *@param  * @param
    *@return {@link User}
    *@description 从安全上下文中获取用户信息
    **/
    public User getLoginUser();

    public int getUserId();
}
