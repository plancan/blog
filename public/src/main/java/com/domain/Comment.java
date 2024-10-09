package com.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author 今昔
 * @description 评论类
 * @date 2022/11/9 16:44
 */
@ApiModel(value = "评论类")
public class Comment {
    @ApiModelProperty(notes = "评论id")
    private int id;
    @ApiModelProperty(notes = "文章id")
    private int articleId;
    @ApiModelProperty(notes = "内容")
    private String content;
    @ApiModelProperty(notes = "评论人id")
    private int commentatorsId;
    @ApiModelProperty("评论人头像")
    private String commentatorsAvatar;
    @ApiModelProperty("评论人姓名")
    private String commentatorsName;
    @ApiModelProperty(notes = "创建时间")
    private long createTime;

    public Comment() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getArticleId() {
        return articleId;
    }

    public void setArticleId(int articleId) {
        this.articleId = articleId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getCommentatorsId() {
        return commentatorsId;
    }

    public void setCommentatorsId(int commentatorsId) {
        this.commentatorsId = commentatorsId;
    }

    public String getCommentatorsAvatar() {
        return commentatorsAvatar;
    }

    public void setCommentatorsAvatar(String commentatorsAvatar) {
        this.commentatorsAvatar = commentatorsAvatar;
    }

    public String getCommentatorsName() {
        return commentatorsName;
    }

    public void setCommentatorsName(String commentatorsName) {
        this.commentatorsName = commentatorsName;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", articleId=" + articleId +
                ", content='" + content + '\'' +
                ", commentatorsId=" + commentatorsId +
                ", commentatorsAvatar='" + commentatorsAvatar + '\'' +
                ", commentatorsName='" + commentatorsName + '\'' +
                ", createTime=" + createTime +
                '}';
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

}
