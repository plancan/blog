package com.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author 今昔
 * @version 1.0
 * @description: 消息类
 * @date 2023/1/10 20:05
 */
public class Message {
    @ApiModelProperty(notes = "消息id")
    private long id;
    @ApiModelProperty(notes = "消息内容")
    private String content;
    @ApiModelProperty(notes = "发送消息用户")
    private int userId;
    @ApiModelProperty(notes = "接收消息用户")
    private int toUserId;
    @ApiModelProperty(notes = "消息发送时间")
    private long createTime;
    @ApiModelProperty(notes = "消息是否已读")
    private boolean readIt;

    public Message() {
    }

    public Message(String content, long time) {
        this.content = content;
        this.createTime = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public boolean isReadIt() {
        return readIt;
    }



    public void setReadIt(boolean read) {
        this.readIt = read;
    }

    public int getToUserId() {
        return toUserId;
    }

    public void setToUserId(int toUserId) {
        this.toUserId = toUserId;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long time) {
        this.createTime = time;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", userId=" + userId +
                ", toUserId=" + toUserId +
                ", createTime=" + createTime +
                ", readIt=" + readIt +
                '}';
    }
}
