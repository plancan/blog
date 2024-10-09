package com.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.domain.Collection;
import com.domain.Like;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author 今昔
 * @description 点赞查询
 * @date 2022/11/16 16:36
 */
@Mapper
public interface LikeMapper extends BaseMapper<Like> {

    /**
    *@param  * @param articleId
     * @param uid
    *@return {@link Like}
    *@description 查询用户对这篇文章点赞情况
    **/
    public Boolean getUserLike(@Param("articleId") int articleId,@Param("uid") int uid);

    /**
    *@param  * @param map
    *@return
    *@description 更新点赞情况
    **/
    public void updateLike(List likes);

    /**
    *@param  * @param articleId
     * @param uid
    *@return {@link Boolean}
    *@description 查询用户是否收藏
    **/
    public Boolean getUserCollection(@Param("articleId") int articleId,@Param("uid") int uid);

    /**
    *@param  * @param list
    *@return
    *@description 更新收藏情况
    **/
    public void updateCollections(List list);

    /**
    *@param  * @param list
    *@return
    *@description 删除收藏情况
    **/
    public void deleteCollections(List list);
    public void addCollection(@Param("articleId") int articleId,@Param("uid") int uid);

    /**
     *@param  * @param list
     *@return
     *@description 删除收藏情况
     **/
    public void deleteCollection(@Param("articleId") int articleId,@Param("uid") int uid);


}
