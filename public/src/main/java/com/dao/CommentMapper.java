package com.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.domain.Comment;
import org.apache.ibatis.annotations.Mapper;


/**
 * @author 今昔
 * @description 评论查询
 * @date 2022/11/10 19:04
 */
@Mapper
public interface CommentMapper extends BaseMapper<Comment> {
    /**
    *@param  * @param comment
    *@return
    *@description 添加一条新评论
    **/
    public void addComment(Comment comment);

}
