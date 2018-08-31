package com.fekpal.web.model;


import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

/**
 * 校社联发布新消息的实体类
 * Created by hasee on 2017/8/26.
 */
public class SauPublishedNewMsg {

    private String messageTitle;

    private String messageContent;

    private Timestamp sendTime;

    private String publishedObject;

    public String getMessageTitle() {
        return messageTitle;
    }

    public void setMessageTitle(String messageTitle) {
        this.messageTitle = messageTitle;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }

    public Timestamp getSendTime() {
        return sendTime;
    }

    public void setSendTime(Timestamp sendTime) {
        this.sendTime = sendTime;
    }

    public String getPublishedObject() {
        return publishedObject;
    }

    public void setPublishedObject(String publishedObject) {
        this.publishedObject = publishedObject;
    }
}
