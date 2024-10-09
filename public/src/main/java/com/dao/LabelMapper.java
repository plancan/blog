package com.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.domain.Label;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author 今昔
 * @description 标签查询类
 * @date 2022/11/11 18:28
 */
@Mapper
public interface LabelMapper extends BaseMapper<Label> {
    /**
    *@param  * @param articleId
    *@return {@link List< Label>}
    *@description 获得一篇文章的标签
    **/
    public List<Label> getLabels(int articleId);

    /**
    *@param  * @param labelId
    *@return {@link List< String>}
    *@description 根据标签获得含有该标签的文章id
    **/
    public List<String> getLabelArticles(int labelId);

    /**
    *@param  * @param labels
    *@return {@link List< Label>}
    *@description 根据id查询对应标签名
    **/
    public List<Label> getLabelByid(List<Label> labels);
    
    /**
    *@param  * @param articleId
    *@return 
    *@description 删除文章id
    **/
    public void deleteLabels(int articleId);
}
