package com.domain;

/**
 * @author 今昔
 * @description 收藏类
 * @date 2022/11/19 20:36
 */
public class Collection {
    private String articleId;
    private String uid;

    public String getArticleId() {
        return articleId;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }

    @Override
    public String toString() {
        return "Collection{" +
                "articleId=" + articleId +
                ", uid=" + uid +
                '}';
    }
}
