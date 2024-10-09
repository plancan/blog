package com.domain;

/**
 * @author 今昔
 * @description 点赞表
 * @date 2022/11/16 16:29
 */
public class Like {
    private String uid;
    private String articleId;
    private boolean liked;

    public boolean getLiked() {
        return liked;
    }

    public void setLiked(boolean liked) {
        this.liked = liked;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getArticleId() {
        return articleId;
    }

    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }

    @Override
    public String toString() {
        return "Like{" +
                "uid='" + uid + '\'' +
                ", articleId='" + articleId + '\'' +
                ", liked='" + liked + '\'' +
                '}';
    }
}
