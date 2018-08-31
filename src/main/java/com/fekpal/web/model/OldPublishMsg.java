package com.fekpal.web.model;

import java.util.Date;

/**
 * 发布消息列表中的历史发布消息列表对象类
 * @author zhangcanlong
 * @date 2018/4/5
 */
public class OldPublishMsg {
    /**
     * 消息id
     */
    private int messageId;
    /**
     * 消息标题
     */
    private String messageTitle;
    /**
     * 发布时间
     */
    private Date sendTime;
    /**
     * 消息类型
     */
    private int messageType;

    public OldPublishMsg() {
    }

    public OldPublishMsg(int messageId, String messageTitle, Date sendTime, int messageType) {
        this.messageId = messageId;
        this.messageTitle = messageTitle;
        this.sendTime = sendTime;
        this.messageType = messageType;
    }

    public int getMessageId() {
        return messageId;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }

    public String getMessageTitle() {
        return messageTitle;
    }

    public void setMessageTitle(String messageTitle) {
        this.messageTitle = messageTitle;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public int getMessageType() {
        return messageType;
    }

    public void setMessageType(int messageType) {
        this.messageType = messageType;
    }
}
