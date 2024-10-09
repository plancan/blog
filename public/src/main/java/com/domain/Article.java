package com.domain;


import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import java.util.List;

/**
 * @author 今昔
 * @description 文章类
 * @date 2022/11/9 16:27
 */
@ApiModel(value = "文章类")
public class Article {
    @ApiModelProperty(notes = "文章id")
    private int id;
    @ApiModelProperty(notes = "作者id")
    private int uid;
    @ApiModelProperty(notes = "作者姓名")
    private String authorName;
    @ApiModelProperty(notes = "作者头像")
    private String authorAvatar;
    @ApiModelProperty(notes = "文章标题")
    private String title;
    @ApiModelProperty(notes = "文章简介")
    private String summary;
    @ApiModelProperty(notes = "文章内容")
    private String content;
    @ApiModelProperty(notes = "html格式内容")
    private String htmlContent;
    @ApiModelProperty(notes = "创作时间")
    private long createTime;
    @ApiModelProperty(notes = "评论列表")
    private List<Comment> commentList;
    @ApiModelProperty(notes = "浏览量")
    private int viewCounts;
    @ApiModelProperty(notes = "评论数")
    private int commentCounts;
    @ApiModelProperty(notes = "收藏量")
    private int collectionCounts;
    @ApiModelProperty(notes = "点赞数")
    private int likeCounts;
    @ApiModelProperty(notes = "是否允许评论")
    private boolean comment;
    @ApiModelProperty(notes = "是否公开")
    private boolean view;
    @ApiModelProperty(notes = "热度")
    private int popularity;
    @ApiModelProperty(notes = "标签表")
    private List<Label> labels;
    @ApiModelProperty(notes = "当前用户是否点了赞")
    private boolean liked;

    @ApiModelProperty(notes = "是否点赞")
    private boolean collected;
    @ApiModelProperty(notes = "文章封面")
    private String cover;
    @ApiModelProperty(notes = "更新时间")
    private long updateTime;
    @ApiModelProperty(notes = "是否原创")
    private boolean original;

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public Article() {
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public boolean getOriginal() {
        return original;
    }

    public void setOriginal(boolean original) {
        this.original = original;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getCommentCounts() {
        return commentCounts;
    }

    public String getHtmlContent() {
        return htmlContent;
    }

    public void setHtmlContent(String htmlContent) {
        this.htmlContent = htmlContent;
    }

    public void setCommentCounts(int commentCounts) {
        this.commentCounts = commentCounts;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }


    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public List<Comment> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<Comment> commentList) {
        this.commentList = commentList;
    }

    public int getViewCounts() {
        return viewCounts;
    }

    public void setViewCounts(int viewCounts) {
        this.viewCounts = viewCounts;
    }


    public int getCollectionCounts() {
        return collectionCounts;
    }

    public void setCollectionCounts(int collectionCounts) {
        this.collectionCounts = collectionCounts;
    }

    public boolean isComment() {
        return comment;
    }

    public boolean isView() {
        return view;
    }

    public boolean isLiked() {
        return liked;
    }

    public boolean isCollected() {
        return collected;
    }

    public boolean isOriginal() {
        return original;
    }

    public int getLikeCounts() {
        return likeCounts;
    }

    public void setLikeCounts(int likeCounts) {
        this.likeCounts = likeCounts;
    }

    public boolean getComment() {
        return comment;
    }

    public void setComment(boolean comment) {
        this.comment = comment;
    }

    public boolean getView() {
        return view;
    }

    public void setView(boolean view) {
        this.view = view;
    }

    public int getPopularity() {
        return popularity;
    }

    public void setPopularity(int popularity) {
        this.popularity = popularity;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getAuthorAvatar() {
        return authorAvatar;
    }

    public void setLiked(boolean liked) {
        this.liked = liked;
    }

    public List<Label> getLabels() {
        return labels;
    }

    public void setLabels(List<Label> labels) {
        this.labels = labels;
    }

    public void setAuthorAvatar(String authorAvatar) {
        this.authorAvatar = authorAvatar;
    }

    public void setCollected(boolean collected) {
        this.collected = collected;
    }


    @Override
    public String toString() {
        return "Article{" +
                "id=" + id +
                ", uid=" + uid +
                ", authorName='" + authorName + '\'' +
                ", authorAvatar='" + authorAvatar + '\'' +
                ", title='" + title + '\'' +
                ", summary='" + summary + '\'' +
                ", content='" + content + '\'' +
                ", htmlContent='" + htmlContent + '\'' +
                ", createTime=" + createTime +
                ", commentList=" + commentList +
                ", viewCounts=" + viewCounts +
                ", commentCounts=" + commentCounts +
                ", collectionCounts=" + collectionCounts +
                ", likeCounts=" + likeCounts +
                ", comment=" + comment +
                ", view=" + view +
                ", popularity=" + popularity +
                ", labels=" + labels +
                ", liked=" + liked +
                ", collected=" + collected +
                ", cover='" + cover + '\'' +
                ", updateTime=" + updateTime +
                ", original=" + original +
                '}';
    }
}
