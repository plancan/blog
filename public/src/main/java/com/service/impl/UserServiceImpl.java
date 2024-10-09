package com.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.constans.Constant;
import com.dao.ArticleMapper;
import com.domain.Article;
import com.service.UserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.dao.UserMapper;
import com.domain.User;
import com.domain.UserVo;
import com.service.login.LoginService;
import com.utils.ImageUpload;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.List;
import java.util.Objects;

/**
 * @author 今昔
 * @description 用户信息查询实现类
 * @date 2022/11/10 20:03
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private LoginService loginService;
    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public User getBriefUser(int uid) {
        QueryWrapper<User> wrapper=new QueryWrapper<User>();
        wrapper.eq("id",uid);
        wrapper.select("name","id","avatar");
        User brief=userMapper.selectOne(wrapper);
        return brief;
    }

    @Override
    public User register(User user) throws IOException {
        user.setIdentity(Constant.USERIDENTITY);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        uploadAvatar(null);
        uploadSpace(null);
        userMapper.addUser(user);
        User login = userMapper.login(user);
        return login;
    }

    @Override
    public User getUserInfo(int id) {
        QueryWrapper<User> wrapper =new QueryWrapper();
        wrapper.eq("id",id);
        wrapper.select("id","name","sex","email","introduction","birthday","avatar","space_picture");
        return userMapper.selectOne(wrapper);
    }

    @Override
    public List<User> getFollowList(int id) {
        return userMapper.getUserFollow(id);
    }

    @Override
    public List<Article> getCollection() {
        List<Article> collection = articleMapper.getCollection(loginService.getLoginUser().getId());
        setAuthorInfo(collection);
        return collection;
    }

    @Override
    public void setAuthorInfo(List<Article> articles) {
        if(!Objects.isNull(articles)) {
            for (Article article : articles) {
                User user = getBriefUser(article.getUid());
                article.setAuthorName(user.getName());
                article.setAuthorAvatar(user.getAvatar());
            }
        }
    }

    @Override
    public void updateUserInfo(User user) {
        UpdateWrapper<User> wrapper=new UpdateWrapper<>();
        wrapper.set("name",user.getName());
        wrapper.set("introduction",user.getIntroduction());
        wrapper.set("birthday",user.getBirthday());
        wrapper.eq("id",loginService.getLoginUser().getId());
        userMapper.update(user,wrapper);

    }

    @Override
    public void updateUserEmail(User user) {
        UpdateWrapper<User> wrapper=new UpdateWrapper<>();
        wrapper.set("email",user.getEmail());
        wrapper.eq("id",loginService.getLoginUser().getId());
        userMapper.update(user,wrapper);
    }

    @Override
    public String uploadAvatar(MultipartFile multiFile) throws IOException {
        //如果头像为空
        if(Objects.isNull(multiFile)) {
//            File file = new File("./src/main/resources/img/1.jpg");
//            InputStream inputStream = new FileInputStream(file);
//            MultipartFile multipartFile = new MockMultipartFile(file.getName(), inputStream);
//            return ImageUpload.uploadAvatar(multipartFile);
            return null;
        }
        else {
            String upload = ImageUpload.uploadAvatar(multiFile);
//            String avatar=userMapper.getAvatar(loginService.getLoginUser().getId());
//            ImageUpload.delete(avatar);
            userMapper.updateAvatar(upload);
            return null;
        }
    }

    @Override
    public String uploadSpace(MultipartFile multitFile) throws IOException {
        //为空责上传默认图片
        if(Objects.isNull(multitFile)) {
//            File file = new File("../public/src/main/resources/img/2.jpg");
//            InputStream inputStream = new FileInputStream(file);
//            MultipartFile multipartFile = new MockMultipartFile(file.getName(), inputStream);
//            return ImageUpload.uploadSpace(multipartFile);
            return null;
        }
        else {
            UpdateWrapper<User> wrapper=new UpdateWrapper();
            String upload = ImageUpload.uploadSpace(multitFile);
//            String space = userMapper.getSpace(loginService.getLoginUser().getId());
//            ImageUpload.delete(space);
            userMapper.updateAvatar(upload);
            return null;
        }
    }

    @Override
    public boolean follow(int id) {
        Integer follow = userMapper.getFollow(loginService.getUserId(), id);
        //如果没有进行关注
        if(Objects.isNull(follow))
        {
            //进行关注
            userMapper.toFollow(loginService.getUserId(),id);
            return true;
        }
        else {
            userMapper.cancelFollow(follow);
            return false;
        }
    }

    @Override
    public void sendMaik(String mail) {
        return;
    }

}
