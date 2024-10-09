package com.domain;

/**
 * @author 今昔
 * @description 文章排序的类
 * @date 2022/11/10 18:12
 */
public class Show {
    private int articleId;
    private int value;

    public int getArticleId() {
        return articleId;
    }

    public void setArticleId(int articleId) {
        this.articleId = articleId;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Show{" +
                "articleId=" + articleId +
                ", value=" + value +
                '}';
    }
}
