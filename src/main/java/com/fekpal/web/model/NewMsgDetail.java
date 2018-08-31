package com.fekpal.web.model;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * 查看新消息的详细信息
 * @authot zhangcanlong
 * @date 2018/4/16
 */
public class NewMsgDetail {
/*
                "receives": null*/
    /**
     * 接受消息表的id
     */
    private  int messageId;
    /**
     * 发送社团的id
     */
    private int orgId;
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
    private Timestamp releaseTime;
    /**
     * 发布人姓名
     */
      private String releaseName;
    /**
     * 消息类型，0表示全体消息，1表示社团内部消息，2表示自定义信息
     */
    private int messageType;
    /**
     * 消息附件
     */
      private String messageAnnex;
    /**
     * 是否已读，0为未读，1为已读
     */
    private int readFlag;

    /**
     * 接受消息人的id
     */
    private List<Integer> receives;

    public List<Integer> getReceives() {
        return receives;
    }

    public void setReceives(List<Integer> receives) {
        this.receives = receives;
    }

    public int getMessageId() {
        return messageId;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }

    public int getOrgId() {
        return orgId;
    }

    public void setOrgId(int orgId) {
        this.orgId = orgId;
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

    public Timestamp getReleaseTime() {
        return releaseTime;
    }

    public void setReleaseTime(Timestamp releaseTime) {
        this.releaseTime = releaseTime;
    }

    public String getReleaseName() {
        return releaseName;
    }

    public void setReleaseName(String releaseName) {
        this.releaseName = releaseName;
    }

    public int getMessageType() {
        return messageType;
    }

    public void setMessageType(int messageType) {
        this.messageType = messageType;
    }

    public String getMessageAnnex() {
        return messageAnnex;
    }

    public void setMessageAnnex(String messageAnnex) {
        this.messageAnnex = messageAnnex;
    }

    public int getReadFlag() {
        return readFlag;
    }

    public void setReadFlag(int readFlag) {
        this.readFlag = readFlag;
    }

    public NewMsgDetail(int messageId, int orgId, String messageTitle, String messageContent, Timestamp releaseTime, String releaseName, int messageType, String messageAnnex, int readFlag, List<Integer> receives) {
        this.messageId = messageId;
        this.orgId = orgId;
        this.messageTitle = messageTitle;
        this.messageContent = messageContent;
        this.releaseTime = releaseTime;
        this.releaseName = releaseName;
        this.messageType = messageType;
        this.messageAnnex = messageAnnex;
        this.readFlag = readFlag;
        this.receives = receives;
    }

    public NewMsgDetail() {

    }
}
