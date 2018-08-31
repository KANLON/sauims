package com.fekpal.web.model;

import java.util.Date;
import java.util.List;

/**
 * 校社联消息发布中查看某条消息的详细消息类
 * @author zhangcanlong
 * @date 2018/4/5
 */
public class SauMsgPublishDetail {
    /**
     * 消息id
     */
    private int messageId;
    /**
     * 消息标题
     */
    private String messageTitle;
    /**
     * 消息内容
     */
    private String messageContent;
    /**
     * 发布时间
     */
    private Date sendTime;
    /**
     * 消息类型
     */
    private int messageType;
    /**
     * 接受消息的对象
     */
    private List<OldPublishMsg> publishedObject;

    public SauMsgPublishDetail() {
    }

    public SauMsgPublishDetail(int messageId, String messageTitle, String messageContent, Date sendTime, int messageType, List<OldPublishMsg> publishedObject) {
        this.messageId = messageId;
        this.messageTitle = messageTitle;
        this.messageContent = messageContent;
        this.sendTime = sendTime;
        this.messageType = messageType;
        this.publishedObject = publishedObject;
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

    public List<OldPublishMsg> getPublishedObject() {
        return publishedObject;
    }

    public void setPublishedObject(List<OldPublishMsg> publishedObject) {
        this.publishedObject = publishedObject;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }
}
