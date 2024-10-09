package com.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author 今昔
 * @description 查询权限
 * @date 2022/11/12 14:33
 */
@Mapper
public interface AuthorityMapper{
    /**
    *@param  * @param id
    *@return
    *@description 查询用户的权限
    **/
    public List<String> getAuthorities(int id);
}
