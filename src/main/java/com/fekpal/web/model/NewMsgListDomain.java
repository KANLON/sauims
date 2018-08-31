package com.fekpal.web.model;


import java.sql.Timestamp;

/**
 * 消息列表的中的消息对象
 * @author zhangcanlong
 * @date 2018/4/16
 */
public class NewMsgListDomain {
    private Integer messageId;
    private String messageTitle;
    private Timestamp releaseTime;
    private String releaseName;
    private Integer readFlag;

    public NewMsgListDomain(){}

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




    public Integer getReadFlag() {
        return readFlag;
    }

    public void setReadFlag(Integer readFlag) {
        this.readFlag = readFlag;
    }

    @Override
    public String toString() {
        return "NewMsgListDomain{" +
                "messageId=" + messageId +
                ", messageTitle='" + messageTitle + '\'' +
                ", releaseTime=" + releaseTime +
                ", releaseName='" + releaseName + '\'' +
                ", readFlag=" + readFlag +
                '}';
    }
}
