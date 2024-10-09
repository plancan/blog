package com.service;

import com.domain.Message;

import java.util.List;
import java.util.Map;

/**
 * @author 今昔
 * @version 1.0
 * @description: 消息
 * @date 2023/1/11 14:03
 */
public interface MessageService {
    void setMessage(Message message);
    Map<Integer,List<Message>> getMessages(int userId);
    List<Message> getNewMessage(int toUserId,int userId);

    void readMessage(List<Message> messages,int userId);
    void storeMessage(List<Message> messages);
}
