package com.service;

import com.dao.UserMapper;
import com.domain.Article;
import com.domain.Label;
import com.domain.User;
import com.domain.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

/**
 * @author 今昔
 * @description 用户信息查询类
 * @date 2022/11/10 20:00
 */
public interface UserService {
    /**
     * @param * @param uid 用户id
     * @return {@link User}
     * @description 查询用户id, 昵称，头像等简单信息
     **/
    public User getBriefUser(int uid);

    /**
     * @param * @param user
     * @return {@link UserVo}
     * @description 注册用户
     **/
    public User register(User user) throws IOException;

    /**
    *@param  * @param
    *@return {@link User}
    *@description 用户查询自己的信息
    **/
    public User getUserInfo(int id);

    /**
    *@param  * @param null
    *@return {@link null}
    *@description 查询用户的关注列表
    **/
    public List<User> getFollowList(int id);

    /**
    *@param  * @param null
    *@return {@link null}
    *@description 用户查询的收藏列表
    **/
    public List<Article> getCollection();

    /**
     *@param  * @param articles
     *@return
     *@description 为文章列表添加作者简要信息
     **/
    public void setAuthorInfo(List<Article> articles);

    /**
    *@param  * @param user
    *@return {@link User}
    *@description 修改用户信息
    **/
    public void updateUserInfo(User user);

    /**
    *@param  * @param user
    *@return
    *@description 修改用户邮箱号
    **/
    public void updateUserEmail(User user);

    /**
    *@param  * @param
    *@return {@link String}
    *@description 上传头像
    **/
    public String uploadAvatar(MultipartFile multipartFile) throws IOException;

    /**
    *@param  * @param
    *@return {@link String}
    *@description 上传空间图片
    **/
    public String uploadSpace(MultipartFile multipartFile) throws IOException;

    /**
    *@param  * @param id
    *@return
    *@description 关注或取消关注
    **/
    public boolean follow(int id);

    void sendMaik(String mail);

}
