package com.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.service.LabelService;
import com.dao.LabelMapper;
import com.domain.Label;
import com.enums.HttpCodeEnum;
import com.exception.SystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 今昔
 * @description 表现查询实现类
 * @date 2022/11/11 18:32
 */
@Service
public class LabelServiceImpl implements LabelService {
    @Autowired
    private LabelMapper labelMapper;
    @Override
    public List<Label> getLabels(int articleId) {
        return labelMapper.getLabels(articleId);
    }

    @Override
    public List<String> getLabelArticles(int labelId) {
        List<String> labelArticles = labelMapper.getLabelArticles(labelId);
        if(labelArticles.size()==0)
        {
            throw new SystemException(HttpCodeEnum.LABEL_NOTEXIST);
        }
        return labelArticles;
    }

    @Override
    public List<Label> getLabelById(List labels) {
        return labelMapper.getLabelByid(labels);
    }

    @Override
    public List<Label> getAllLabels() {
        QueryWrapper<Label> wrapper=new QueryWrapper<>();
        wrapper.select("label_id","label");
        return labelMapper.selectList(wrapper);
    }

    @Override
    public void deleteLabels(int articleId) {
        labelMapper.deleteLabels(articleId);
    }
}
