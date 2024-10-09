package com.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.domain.Article;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author 今昔
 * @description 文章查询接口
 * @date 2022/11/9 16:41
 */
@Mapper
public interface ArticleMapper extends BaseMapper<Article> {
    /**
    *@param  * @param id
    *@return {@link Article}
    *@description 查询一篇文章，不包含其标签,作者简要信息
    **/
    public Article getArticle(int id);

    /**
    *@param  * @param article
    *@return
    *@description 添加文章信息
    **/
    public void addArticle(Article article);

    /**
    *@param  * @param article
    *@return
    *@description 为文章添加图片
    **/
    public void addPictures(Article article);

    /**
    *@param  * @param article
    *@return
    *@description 为文章添加标签
    **/
    public void addLabels(Article article);

   /**
   *@param  * @param id
   *@return {@link List< Article>}
   *@description 获得收藏列表
   **/
    public List<Article> getCollection(int id);

    /**
    *@param  * @param
    *@return {@link List< Integer>}
    *@description 获得所有文章id
    **/
    public List<String> getAllArticleId();

    /**
    *@param  * @param articleId
    *@return
    *@description 删除文章的图片
    **/
    public void deletePictures(int articleId);

    /**
    *@param  * @param article
    *@return
    *@description 更新文章
    **/
    public void updateArticle(Article article);
}
