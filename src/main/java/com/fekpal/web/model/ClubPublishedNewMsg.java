package com.fekpal.web.model;


import java.sql.Timestamp;

/**
 * 社团发布新消息的实体类，暂时跟校社联的一样
 * @author zhangcanlong
 * @date 2018/4/9
 */
public class ClubPublishedNewMsg {
    private String messageTitle;
    private String messageContent;
    private Timestamp sendTime;
    private String publishedObject;
    public ClubPublishedNewMsg(){}

    public String getPublishedObject() {
        return publishedObject;
    }

    public void setPublishedObject(String publishedObject) {
        this.publishedObject = publishedObject;
    }

    public ClubPublishedNewMsg(String messageTitle, String messageContent, Timestamp sendTime, String publishedObject) {

        this.messageTitle = messageTitle;
        this.messageContent = messageContent;
        this.sendTime = sendTime;
        this.publishedObject = publishedObject;
    }

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





    @Override
    public String toString() {
        return "SauPublishedNewMsg{" +
                "messageTitle='" + messageTitle + '\'' +
                ", messageContent='" + messageContent + '\'' +
                ", sendTime=" + sendTime +
                ", publishedObject=" + publishedObject +
                '}';
    }
}
