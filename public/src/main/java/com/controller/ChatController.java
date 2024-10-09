package com.controller;

import com.domain.LoginUser;
import com.domain.Resp;
import com.service.MessageService;
import com.service.login.LoginService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @author 今昔
 * @version 1.0
 * @description: 聊天接口
 * @date 2023/3/3 17:14
 */
@RestController
@CrossOrigin
@RequestMapping("/blog/chat")
public class ChatController {
    @Autowired
    private MessageService messageService;
    @Autowired
    private LoginService loginService;
    @GetMapping("/getMessages")
    @ApiOperation(value = "获得历史消息,返回结果以与该用户聊天的另一方为key,value为双方聊天记录")
    public Resp getMessageCounts(){
        return new Resp(messageService.getMessages(loginService.getUserId()));
    }

//    @GetMapping("/chat/getMessage/{toUserId}")
//    @ApiOperation(value = "获得离线时某个用户发送的全部消息")
//    @ApiImplicitParams(
//            {
//                    @ApiImplicitParam(name = "toUserId",value = "发送对象id",required = true),
//            }
//    )
////    public Resp getMessage(@PathVariable int toUserId){
//////        messageService.deleteMessage(toUserId,loginService.getUserId());
////        return new Resp(messageService.getMessages(toUserId,loginService.getUserId()));
////    }
}
