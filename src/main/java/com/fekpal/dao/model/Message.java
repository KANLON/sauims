package com.fekpal.dao.model;

import com.fekpal.common.base.BaseModel;

import java.sql.Timestamp;

/**
 * 消息实体类
 * @author zhangcanlong
 */
public class Message extends BaseModel {

    private static final long serialVersionUID = 7490442249324832127L;

    /**
     * 消息自增id
     */
    private Integer messageId;

    /**
     * 发布人或社团id
     */
    private Integer orgId;

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
     * 发布人名称
     */
    private String releaseName;

    /**
     * 消息类型 0:全体信息 1:社团内消息 2是自定义接受人的消息
     */
    private Integer messageType;

    /**
     * 消息附件
     */
    private String messageAnnex;

    private Integer messageState;

    public String getReleaseName() {
        return releaseName;
    }

    public void setReleaseName(String releaseName) {
        this.releaseName = releaseName;
    }

    public Integer getOrgId() {
        return orgId;
    }

    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
    }

    public Integer getMessageId() {
        return messageId;
    }

    public void setMessageId(Integer messageId) {
        this.messageId = messageId;
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

    public Integer getMessageType() {
        return messageType;
    }

    public void setMessageType(Integer messageType) {
        this.messageType = messageType;
    }

    public String getMessageAnnex() {
        return messageAnnex;
    }

    public void setMessageAnnex(String messageAnnex) {
        this.messageAnnex = messageAnnex;
    }

    public Integer getMessageState() {
        return messageState;
    }

    public void setMessageState(Integer messageState) {
        this.messageState = messageState;
    }
}
