package com.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.domain.Message;
import com.domain.Resp;
import com.service.MessageService;
import com.service.login.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author 今昔
 * @version 1.0
 * @description: 聊天接口
 * @date 2023/3/3 16:33
 */
@ServerEndpoint("/client/{token}")
@Component
public class Chat {
    // 储存在线的聊天连接，使用线程安全的CurrentHashMap.
    // volatile当多个线程访问同一个变量时，一个线程修改了这个变量的值，其他线程能够立即看得到修改的值。
    private static volatile ConcurrentHashMap<Integer, Chat> socket = new ConcurrentHashMap<>();
    /**
     * 与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    private Session session;

    /**
     * spring管理的都是单例，和 websocket （多对象）相冲突。因为websocket是多实例单线程的，
     * 每一个新用户连接时都会创建一个新的实例，而websocket中的对象在@Autowried时，
     * 只有整个项目启动时会注入，而之后新的websocket实例都不会再次注入，故websocket上@Autowried的bean是会为null。
     */
    private static MessageService messageService;

    private static LoginService loginService;
    /**
     * 接收userId
     */
    private int userId;

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("token") String token) {
        //验证登陆信息
        loginService.valid(token);
        this.session = session;
        this.userId = loginService.getUserId();
        if (socket.containsKey(userId)) {
            socket.remove(userId);
            socket.put(userId, this);
        } else {
            socket.put(userId, this);
        }
        try {
            Resp resp = new Resp();
            sendMessage(JSONObject.toJSONString(resp));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose(Session session) {
        if (socket.containsKey(userId)) {
            socket.remove(userId);
        }
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message, Session session) throws IOException {
        if (message != null && !message.isEmpty()) {
            Message meg = JSONObject.parseObject(message, Message.class);
            meg.setUserId(this.userId);
            meg.setCreateTime(System.currentTimeMillis());
            int toUserId = meg.getToUserId();
            if (socket.containsKey(toUserId)) {
                meg.setReadIt(true);
                //向目标用户发送消息
                socket.get(toUserId).sendMessage(JSONObject.toJSONString(meg));
            } else {
                //如果对方不在线，把消息设置为未读
                meg.setReadIt(false);
            }
            //   保存聊天记录
            messageService.setMessage(meg);
        }
    }


    /**
     * 服务端发送消息给客户端
     */
    private void sendMessage(String message) throws IOException {
        session.getBasicRemote().sendText(message);
    }

    @Autowired
    private void setMessageService(MessageService message) {
        messageService = message;
    }

    @Autowired
    private void setLoginService(LoginService login) {
        loginService = login;
    }
}