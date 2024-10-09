package com.dao;

import com.domain.Message;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author 今昔
 * @version 1.0
 * @description: 消息
 * @date 2023/1/11 14:02
 */
@Mapper
public interface MessageMapper extends BaseMapper<Message> {
    public void storeMessage(List<Message> messages);
}
