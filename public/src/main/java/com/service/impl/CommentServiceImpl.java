package com.service.impl;



import com.alibaba.fastjson.JSON;
import com.constans.Constant;
import com.dao.CommentMapper;
import com.domain.Article;
import com.enums.HttpCodeEnum;
import com.exception.SystemException;
import com.service.CommentService;
import com.service.UserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.domain.Comment;
import com.domain.User;
import com.service.login.LoginService;
import com.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author 今昔
 * @description 评论查询实现类
 * @date 2022/11/10 19:07
 */
@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    UserService userService;
    @Autowired
    private LoginService loginService;
    @Autowired
    private RedisUtil redisUtil;
    @Override
    public List<Comment> getComments(int articleId) {
        QueryWrapper<Comment> queryWrapper = new QueryWrapper();
        queryWrapper.select("id","commentators_id","article_id","content","create_time");
        queryWrapper.eq("article_id",articleId);
        List<Comment> comments = commentMapper.selectList(queryWrapper);
        for(Comment comment:comments)
        {
            User brief= userService.getBriefUser(comment.getCommentatorsId());
            comment.setCommentatorsAvatar(brief.getAvatar());
            comment.setCommentatorsName(brief.getName());
        }
        List<Comment> collect = comments.stream().sorted(Comparator.comparing(Comment::getCreateTime).reversed()).collect(Collectors.toList());
        return collect;
    }

    @Override
    public void sentComment(Comment comment) {
        int uid=loginService.getUserId();
        User brief= userService.getBriefUser(uid);
        comment.setCommentatorsAvatar(brief.getAvatar());
        comment.setCommentatorsName(brief.getName());
        comment.setCommentatorsId(uid);
        comment.setCreateTime(System.currentTimeMillis());
        //添加文章评论数
        Article article= (Article) redisUtil.hget(Constant.ARTICLEKEY , String.valueOf(comment.getArticleId()));
        if(Objects.isNull(article)){
            throw new SystemException(HttpCodeEnum.ARTICLE_NOTEXIST);
        }
        List<Comment> comments=article.getCommentList();
        if(Objects.isNull(comments))
        {
            comments=new LinkedList<>();
        }
        commentMapper.addComment(comment);
        comments.add(comment);
        article.setCommentList(comments);
        article.setCommentCounts(article.getCommentCounts()+1);
        redisUtil.hset(Constant.ARTICLEKEY,String.valueOf(article.getId()),article,Constant.ARTICLSTORETIME);
        //在定时任务加
    }

    @Override
    public void deleteComment(Comment comment) {
        Article article= (Article) redisUtil.hget(Constant.ARTICLEKEY , String.valueOf(comment.getArticleId()));
        if(Objects.isNull(article)){
            throw new SystemException(HttpCodeEnum.ARTICLE_NOTEXIST);
        }
        List<Comment> comments= article.getCommentList().stream()
                .filter(comment1 -> comment1.getId()!=comment.getId()).collect(Collectors.toList());
        article.setCommentList(comments);
        article.setCommentCounts(article.getCommentCounts()-1);
        redisUtil.hset(Constant.ARTICLEKEY,String.valueOf(comment.getArticleId()),article,Constant.ARTICLSTORETIME);
        commentMapper.deleteById(comment);
    }
}
