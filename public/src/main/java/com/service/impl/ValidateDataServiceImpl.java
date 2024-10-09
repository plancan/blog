package com.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dao.UserMapper;
import com.domain.Article;
import com.domain.Comment;
import com.domain.User;
import com.service.ArticleService;
import com.service.ValidateDataService;
import com.service.login.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @author 今昔
 * @description 校验数据实现类
 * @date 2022/11/13 14:35
 */
@Service
public class ValidateDataServiceImpl implements ValidateDataService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private LoginService loginService;
    @Autowired
    private ArticleService articleService;
    private static final String rightMail = "[a-zA-Z0-9]+@[a-zA-Z0-9]+\\.[a-zA-Z0-9]+";
    private static final int maxPasswordLength = 32;
    private static final int nameLength = 16;
    private static final int minPasswordLength = 8;
    private static final int introductionLength = 64;
    private static final String man = "男";
    private static final String woman = "女";
    private static final int titleLength = 24;
    private static final int summaryLength = 64;
    private static final int maxLabelCount = 5;
    private static final int minLabelCount=1;
    private static final int commentLength=256;

    @Override
    public boolean validateEmail(String email) {
        if (email.matches(rightMail)) {
            String idByEmail = userMapper.getIdByEmail(email);
            if (Objects.isNull(idByEmail)) {
                return true;
            }
            return false;
        }
        return false;
    }

    @Override
    public boolean validateUserInfo(User user) {
        //昵称不为空且不超出最大长度
        if (Objects.isNull(user.getName()) || "".equals(user.getName()) || user.getName().length() > nameLength) {
            return false;
        }
        //密码校验
        if (Objects.isNull(user.getPassword()) || user.getPassword().length() < minPasswordLength || user.getPassword().length() > maxPasswordLength) {
            return false;
        }

//        //出生日期校验
//        if (user.getBirthday() > 0 && System.currentTimeMillis() > user.getBirthday()) {
//            return false;
//        }
//        //个人简介校验
//        if (!Objects.isNull(user.getIntroduction()) && user.getIntroduction().length() > introductionLength) {
//            return false;
//        }
//        //性别校验
//        if (!Objects.isNull(user.getSex()) && !man.equals(user.getSex()) && !woman.equals(user.getSex())) {
//            return false;
//        }
        return true;
    }

    @Override
    public boolean validateArticle(Article article) {
        //标题验证
        if (Objects.isNull(article.getTitle()) || article.getTitle().length() > titleLength) {
            return false;
        }
        //验证内容
        if (Objects.isNull(article.getContent())) {
            return false;
        }
        //验证简介
        if (!Objects.isNull(article.getSummary()) && article.getSummary().length() > summaryLength) {
            return false;
        }
        //标签验证
        if(Objects.isNull(article.getLabels())||
                article.getLabels().size()>maxLabelCount||article.getLabels().size()<minLabelCount ){
            return false;
        }
        return true;
    }

    @Override
    public boolean validateComment(Comment comment) {
        //如果文章不允许评论
        if(!articleService.getArticle(comment.getArticleId()).getComment()){
            return false;
        }
        if(Objects.isNull(comment.getContent())||comment.getContent().length()>commentLength){
            return false;
        }
        return true;
    }

    @Override
    public boolean validatePassword(String oldPassword,String newPassword) {
        if(!passwordEncoder.matches(oldPassword,loginService.getLoginUser().getPassword())){
            return false;
        }
        if(Objects.isNull(newPassword)||newPassword.length()<minPasswordLength||newPassword.length()>maxPasswordLength){
            return false;
        }
        return true;
    }

    @Override
    public boolean validatePassword(String password) {
        if(Objects.isNull(password)||password.length()<minPasswordLength||password.length()>maxPasswordLength){
            return false;
        }
        return true;
    }

}
