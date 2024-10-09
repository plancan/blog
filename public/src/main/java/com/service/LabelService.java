package com.service;

import com.domain.Label;

import java.util.List;

/**
 * @author 今昔
 * @description 标签查询接口
 * @date 2022/11/11 18:31
 */
public interface LabelService {
    /**
    *@param  * @param articleId
    *@return {@link List< Label>}
    *@description 查询一篇文章的标签
    **/
    public List<Label> getLabels(int articleId);
    /**
    *@param  * @param labelId
    *@return {@link List< Integer>}
    *@description 根据种类id查询文章id
    **/
    public List<String> getLabelArticles(int labelId);

    /**
    *@param  * @param null
    *@return {@link null}
    *@description 根据文章的标签id列表来查询标签
    **/
    public List<Label> getLabelById(List labels);
    /**
    *@param  * @param
    *@return {@link List< Label>}
    *@description 获得所有可选标签
    **/
    public List<Label> getAllLabels();

    /**
    *@param  * @param articleId
    *@return
    *@description 删除文章标签
    **/
    public void deleteLabels(int articleId);
}
