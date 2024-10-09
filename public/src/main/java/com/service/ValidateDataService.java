package com.service;

import com.domain.Article;
import com.domain.Comment;
import com.domain.User;

/**
 * @author 今昔
 * @description 对前端的数据进行校验
 * @date 2022/11/13 14:35
 */
public interface ValidateDataService {
    /**
    *@param  * @param email
    *@return {@link boolean}
    *@description 校验邮箱号是否正确，是否重复
    **/
    public boolean validateEmail(String email);

    /**
    *@param  * @param user
    *@return {@link boolean}
    *@description 验证用户信息
    **/
    public boolean validateUserInfo(User user);

    /**
    *@param  * @param article
    *@return {@link boolean}
    *@description 验证文章
    **/
    public boolean validateArticle(Article article);

    /**
    *@param  * @param comment
    *@return {@link boolean}
    *@description 检查评论合法性
    **/
    public boolean validateComment(Comment comment);

    /**
    *@param  * @param password
    *@return {@link boolean}
    *@description 检查密码
    **/
    public boolean validatePassword(String oldPassword,String password);

    /**
    *@param  * @param password
    *@return {@link boolean}
    *@description 检查密码
    **/
    public boolean validatePassword(String password);
}
