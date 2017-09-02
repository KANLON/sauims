package com.fekpal.domain.controllerDomain;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 校社联发布新消息的实体类
 * Created by hasee on 2017/8/26.
 */
public class ClubPublishedNewMsgDomain {
    private String messageTitle;
    private String messageContent;
    private Date sendTime;
    private List<Map<String,Integer>> publishedObject;
    public ClubPublishedNewMsgDomain(){}

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

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public List<Map<String,Integer>> getPublishedObject() {
        return publishedObject;
    }

    public void setPublishedObject(List<Map<String,Integer>> publishedObject) {
        this.publishedObject = publishedObject;
    }

    @Override
    public String toString() {
        return "SauPublishedNewMsgDomain{" +
                "messageTitle='" + messageTitle + '\'' +
                ", messageContent='" + messageContent + '\'' +
                ", sendTime=" + sendTime +
                ", publishedObject=" + publishedObject +
                '}';
    }
}
