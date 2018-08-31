package com.fekpal.service.model.domain;

import com.fekpal.common.base.BaseModel;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by APone on 2018/2/17 1:39.
 * 发送信息封装
 */
public class SRMsgRecord extends BaseModel {

    private static final long serialVersionUID = -7551797442934277703L;

    /**
     * 降序
     */
    public static final String DESC = "desc";

    /**
     * 升序
     */
    public static final String ASC = "asc";

    /**
     * 信息标题
     */
    private String messageTitle;

    /**
     * 信息内容
     */
    private String messageContent;

    /**
     * 发送时间
     */
    private Timestamp releaseTime;

    /**
     * 信息附件
     */
    private MultipartFile messageAnnex;

    /**
     * 信息标识集
     */
    private List<Integer> ids;

    /**
     * 接收人标识集
     */
    private List<Integer> receives;

    /**
     * 排序
     */
    private String order;

    public List<Integer> getReceives() {
        return receives;
    }

    public void setReceives(List<Integer> receives) {
        this.receives = receives;
    }

    public List<Integer> getIds() {
        return ids;
    }

    public void setIds(List<Integer> ids) {
        this.ids = ids;
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

    public MultipartFile getMessageAnnex() {
        return messageAnnex;
    }

    public void setMessageAnnex(MultipartFile messageAnnex) {
        this.messageAnnex = messageAnnex;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }
}
