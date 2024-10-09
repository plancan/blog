package com.service.login;

import com.domain.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * @author 今昔
 * @description 自定义登陆
 * @date 2022/11/12 10:06
 */
public interface UserDetailService extends UserDetailsService {
    /**
    *@param  * @param user
    *@return {@link UserDetails}
    *@description 查询用户信息
    **/
    public UserDetails loadUserByUsername(User user);
}
