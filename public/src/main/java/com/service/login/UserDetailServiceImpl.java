package com.service.login;

import com.dao.AuthorityMapper;
import com.dao.UserMapper;
import com.domain.LoginUser;
import com.domain.User;
import com.enums.HttpCodeEnum;
import com.exception.SystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * @author 今昔
 * @description 登录查询密码
 * @date 2022/11/11 22:01
 */
@Service
public class UserDetailServiceImpl implements UserDetailService {
    @Autowired
    private AuthorityMapper authorityMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(User user) throws UsernameNotFoundException {
        User u = userMapper.login(user);
        if (!Objects.isNull(u) && passwordEncoder.matches(user.getPassword(), u.getPassword())) {
            List<String> authorities = authorityMapper.getAuthorities(user.getId());
            return new LoginUser(u, authorities);
        } else {
            throw new SystemException(HttpCodeEnum.LOGIN_ERROR);
        }

    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }
}
