package com.service;

import com.domain.User;

import java.util.Map;

/**
 * @author 今昔
 * @description 验证码发送类
 * @date 2022/11/13 13:50
 */
public interface VerifyService {
    /**
    *@param  * @param
    *@return {@link String}
    *@description 发送图片验证码
    **/
    public Map getImageVerify();

    /**
    *@param  * @param email
    *@return 
    *@description 获取邮箱验证码
    **/
    public void getEmailVerify(String email);

    /**
    *@param  * @param verify
     * @param email
    *@return {@link boolean}
    *@description 进行邮箱验证码校验
    **/
    public boolean validateEmailVerify(int verify,String email);

    /**
    *@param  * @param verify
     * @param key
    *@return {@link boolean}
    *@description 进行图片验证码的校验
    **/
    public boolean validateImageVerify(String verify,String key);
}
