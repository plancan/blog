package com.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.constans.Constant;
import com.dao.MessageMapper;
import com.google.gson.JsonObject;
import com.qiniu.util.Json;
import com.service.MessageService;
import com.utils.MyUtils;
import com.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.domain.Message;

import javax.swing.event.ListDataEvent;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author 今昔
 * @version 1.0
 * @description: 消息实现类
 * @date 2023/1/11 14:03
 */
@Service
public class MessageServiceImpl implements MessageService {
    @Autowired
    private MessageMapper messageMapper;
    @Autowired
    private RedisUtil redisUtil;

    @Override
    public void setMessage(Message message) {
//        messageMapper.insert(message);
        // 取出之前的聊天记录,聊天记录包括双方的聊天记录
        Object mine = redisUtil.hget(
                Constant.MESSAGE, String.valueOf(message.getUserId()));//这是发送方
        Object other = redisUtil.hget(Constant.MESSAGE, String.valueOf(message.getToUserId()));//这是接收方
        List<Message> mineMessages = MyUtils.castList(mine, Message.class);
        List<Message> otherMessages = MyUtils.castList(other, Message.class);
        if (Objects.isNull(mineMessages)) {
            mineMessages = new LinkedList<>();
        }
        if (Objects.isNull(otherMessages)) {
            otherMessages = new LinkedList<>();
        }
        otherMessages.add(message);
        mineMessages.add(message);
        redisUtil.hset(Constant.MESSAGE, String.valueOf(message.getUserId()), mineMessages);
        redisUtil.hset(Constant.MESSAGE, String.valueOf(message.getToUserId()), otherMessages);
    }

    @Override
    public Map<Integer,List<Message>> getMessages(int userId) {
        Object o = redisUtil.hget(Constant.MESSAGE, String.valueOf(userId));
        List<Message> megs = MyUtils.castList(o, Message.class);
        if (Objects.isNull(megs)) {
            megs=getNewMessage(userId,userId);
            if(Objects.isNull(megs)){
                return null;
            }
        }
        List<Message> messages=megs.stream().sorted(Comparator.comparing(Message::getCreateTime).reversed()).collect(Collectors.toList());
        // 该表以与该用户聊天的另一方为key,value为双方聊天记录
        Map<Integer, List<Message>> messageMap = new HashMap<>();
        for (Message message : messages) {
            int key;
            //说明该用户是消息发送方
            if (message.getUserId() == userId) {
                key=message.getToUserId();
                message.setReadIt(true);
            }
            else {
                key=message.getUserId();
            }
            List<Message> list;
            //如果其他用户-》该用户键值对,添加，没有创建
            if (!messageMap.containsKey(key)) {
                list = new LinkedList<>();
            } else {
                list = messageMap.get(key);
            }
            list.add(message);
            messageMap.put(key,list);
        }
        readMessage(messages,userId);
        return messageMap;
    }

    @Override
    public List<Message> getNewMessage(int toUserId, int userId) {
        QueryWrapper<Message> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("id", "user_id", "to_user_id", "content", "create_time","read_it");
        queryWrapper.eq("to_user_id", userId);
        queryWrapper.or();
        queryWrapper.eq("user_id", userId);
        return messageMapper.selectList(queryWrapper);
    }

    @Override
    public void readMessage(List<Message> messages,int userId) {
        List<Message> list = JSON.parseArray(JSON.toJSONString(messages), Message.class);
        list.stream().forEach(message -> message.setReadIt(true));
        redisUtil.hset(Constant.MESSAGE,String.valueOf(userId),list);
//        QueryWrapper<Message> queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq("to_user_id", toUserId);
//        queryWrapper.eq("user_id", userId);
//        messageMapper.delete(queryWrapper);
    }

    @Override
    public void storeMessage(List<Message> messages) {
        messageMapper.storeMessage(messages);
    }
}
