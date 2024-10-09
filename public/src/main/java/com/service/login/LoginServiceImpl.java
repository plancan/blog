package com.service.login;

import com.constans.Constant;
import com.domain.LoginUser;
import com.domain.User;
import com.domain.UserVo;
import com.enums.HttpCodeEnum;
import com.exception.SystemException;
import com.utils.Jwt;
import com.utils.RedisUtil;
import io.jsonwebtoken.Claims;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @author 今昔
 * @description 登录服务实现类
 * @date 2022/11/11 21:52
 */
@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    private AuthenticationProvider authenticationProvider;
    @Autowired
    private RedisUtil redisUtil;

    @Override
    public void valid(String token) {
        Claims claims = null;
        try {
            claims = Jwt.parseJWT(token);
        } catch (Exception e) {
            throw new SystemException(HttpCodeEnum.WITHOUT_LOGIN);
        }
        int id = (int) claims.get("id");
        LoginUser loginUser = (LoginUser) redisUtil.get(Constant.LOGINUSERKEY + id);
//        LoginUser loginUser = JSON.parseObject(JSON.toJSONString(o), LoginUser.class);
        if (Objects.isNull(loginUser)) {
            throw new SystemException(HttpCodeEnum.WITHOUT_LOGIN);
        }
        //获取权限信息封装到authenticationToken
        //每一位通过认证的用户都有自己的SercurityContext
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginUser, null, loginUser.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }

    @Override
    public UserVo login(User user) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(user, user.getPassword());
        //传入自定义的provider校验
        Authentication authentication = authenticationProvider.authenticate(authenticationToken);
        if (Objects.isNull(authentication)) {
            throw new SystemException(HttpCodeEnum.LOGIN_ERROR);
        }
        //更据id生成token
        String token = Jwt.getJwtToken(user.getId());
        LoginUser u = (LoginUser) authentication.getPrincipal();
        //将用户信息存入redis
        redisUtil.set(Constant.LOGINUSERKEY + user.getId(), u, Constant.LOGINUSERTIME);
        //vo优化
        UserVo userVo = new UserVo();
        BeanUtils.copyProperties(u.getUser(), userVo);
        userVo.setToken(token);
        return userVo;
    }

    @Override
    public void logout() {
        int id = getLoginUser().getId();
        redisUtil.del(Constant.LOGINUSERKEY+id);
    }

    @Override
    public User getLoginUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        return loginUser.getUser();

    }

    @Override
    public int getUserId() {
//        return 2;
        return getLoginUser().getId();
    }
}
