package com.controller;

import com.service.CommentService;
import com.domain.Comment;
import com.domain.Resp;
import com.enums.HttpCodeEnum;
import com.service.ValidateDataService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author 今昔
 * @description 评论接口
 * @date 2022/11/13 11:06
 */
@CrossOrigin
@RestController
@RequestMapping("/blog/comment")
@Api(tags = "评论信息接口")
public class CommentController {
    @Autowired
    private CommentService commentService;
    @Autowired
    private ValidateDataService validateDataService;

    @PostMapping("/sendComment")
    @ApiOperation(nickname = "发评论",value = "发送评论，需要登录")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "comment",value = "评论对象",required = true)}
    )
    public Resp sendComment(@RequestBody Comment comment){
        //验证通过后才能发送评论
        if(!validateDataService.validateComment(comment))
        {
            return new Resp(HttpCodeEnum.COMMENT_ERROR);
        }
        commentService.sentComment(comment);
        return new Resp();
    }

    @DeleteMapping("/deleteComment")
    @ApiOperation(nickname = "删除评论",value = "删评论，需要登录")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "comment",value = "需评论的id,文章id",required = true)}
    )
    public Resp deleteComment(@RequestBody Comment comment){
        commentService.deleteComment(comment);
        return new Resp();
    }
}
