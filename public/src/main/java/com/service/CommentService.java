package com.service;

import com.domain.Comment;

import java.util.List;

/**
 * @author 今昔
 * @description 评论查询接口
 * @date 2022/11/10 19:05
 */
public interface CommentService {
    /**
    *@param  * @param articleId
    *@return {@link List< Comment>}
    *@description 查询一篇文章的所有评论
    **/
    public List<Comment> getComments(int articleId);
    /**
    *@param  * @param comment
    *@return
    *@description 发送评论
    **/
    public void sentComment(Comment comment);

    void deleteComment(Comment comment);
}
