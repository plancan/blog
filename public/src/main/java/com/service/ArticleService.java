package com.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.domain.Article;
import com.domain.Like;
import com.domain.MyPage;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author 今昔
 * @description 文章查询接口
 * @date 2022/11/9 16:17
 */
public interface ArticleService {
    /**
     * @param *    @param currentPage 当前页
     * @param size 每页条数
     * @return {@link MyPage< List<Article>>} 返回分页对象
     * @description 获得热门文章分页
     **/
    public IPage<Article> hotArticleList(int currentPage, int size);

    /**
     * @param * @param id
     * @return {@link Article}
     * @description 查询一篇文章，包含其所有信息
     **/
    public Article getArticle(int id);

    /**
     * @param * @param article
     * @return {@link int}
     * @description 计算文章热度
     **/
    public void setPopularity(Article article);

    /**
     * @param * @param labelId
     * @return {@link IPage< Article>}
     * @description 查询某一类的文章
     **/
    public IPage<Article> getLabelArticles(int labelId, int current, int size);

    /**
    *@param  * @param keyWord
     * @param currrent
     * @param size
    *@return {@link IPage< Article>}
    *@description 根据关键词查询
    **/
    public IPage<Article> getArticlesByKeyword(String keyWord,int currrent,int size);

    /**
    *@param  * @param article
    *@return
    *@description 添加一篇文章
    **/
    public void addArticle(Article article);

    /**
    *@param  * @param articleId
    *@return
    *@description 删除一篇文章
    **/
    public void deleteArticle(int articleId);

    /**
     *@param  * @param article
     *@return
     *@description 对文章进行更新
     **/
    public void updateArticle(Article article);

    /**
    *@param  * @param id
    *@return {@link IPage< Article>}
    *@description 查询其他用户的作品
    **/
    public IPage<Article> getUserArticles(int id,int current ,int size);

    /**
    *@param  * @param current
     * @param size
    *@return {@link IPage< Article>}
    *@description 查询自己的作品
    **/
    public IPage<Article> getMyArticles(int current,int size);

    /**
    *@param  * @param
    *@return {@link List<String>}
    *@description 获得所有文章id
    **/
    public List<String> getAllArticleId();

    /**
    *@param  * @param null
    *@return {@link null}
    *@description 这是更新浏览量，点赞，收藏等
    **/
    public void updateArticleInfo(Article article);

    /**
    *@param  * @param articleId
    *@return
    *@description 点赞或取消点赞
    **/
    public boolean likeArticle(int articleId);

    /**
    *@param  * @param articleId
    *@return {@link boolean}
    *@description 查询当前用户是否对这篇文章点了赞
    **/
    public boolean getLiked(int articleId);

    /**
    *@param  * @param articleId
    *@return {@link boolean}
    *@description 查询用户是否收藏了这篇文章
    **/
    public boolean getCollected(int articleId);

    /**
    *@param  * @param articleId
    *@return {@link boolean}
    *@description 收藏或取消收藏
    **/
    public boolean collectArticle(int articleId);

    /**
    *@param  * @param
    *@return {@link IPage< Article>}
    *@description 获得用户的推荐文章
    **/
    public IPage<Article> getUserRecommend(int current,int size);


   /**
   *@param  * @param labelId
    * @param uid
   *@return {@link List< Article>}
   *@description 返回某个用户某一类文章
   **/
    public IPage<Article> getUserArticleByLabelId(int labelId,int uid,int current,int size);

    public void autoSave(Article article);

    public Article getScript();

}
