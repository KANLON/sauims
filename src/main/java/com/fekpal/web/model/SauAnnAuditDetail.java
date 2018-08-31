package com.fekpal.web.model;

import java.util.Date;

/**
 * 校社联年度审核查看具体某个审核消息的类
 * @author zhangcanlong
 * @date 2018/4/6
 */
public class SauAnnAuditDetail {
    /**
     * 审核信息ID
     */
    private int auditMsgId;
    /**
     * 社团名称c
     */
    private String clubName;
    /**
     * 社长名字
     */
    private String adminName;
    /**
     * 注册时间
     */
    private Date submitTime;
    /**
     * 申请文件
     */
    private String fileName;
    /**
     * 具体内容
     */
    private String description;

    /**
     * 审核状态（0：驳回。1：通过。2审核中）
     */
    private int auditState;
    /**
     * 审核结果
     */
    private String auditResult;
    /**
     * 审核时间
     */
    private Date auditTime;

    public int getAuditMsgId() {
        return auditMsgId;
    }

    public void setAuditMsgId(int auditMsgId) {
        this.auditMsgId = auditMsgId;
    }

    public String getClubName() {
        return clubName;
    }

    public void setClubName(String clubName) {
        this.clubName = clubName;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public Date getSubmitTime() {
        return submitTime;
    }

    public void setSubmitTime(Date submitTime) {
        this.submitTime = submitTime;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public SauAnnAuditDetail() {

    }

    public int getAuditState() {
        return auditState;
    }

    public void setAuditState(int auditState) {
        this.auditState = auditState;
    }

    public String getAuditResult() {
        return auditResult;
    }

    public void setAuditResult(String auditResult) {
        this.auditResult = auditResult;
    }

    public Date getAuditTime() {
        return auditTime;
    }

    public void setAuditTime(Date auditTime) {
        this.auditTime = auditTime;
    }

    public SauAnnAuditDetail(int auditMsgId, String clubName, String adminName, Date submitTime, String fileName, String description, int auditState, String auditResult, Date auditTime) {
        this.auditMsgId = auditMsgId;
        this.clubName = clubName;
        this.adminName = adminName;
        this.submitTime = submitTime;
        this.fileName = fileName;
        this.description = description;
        this.auditState = auditState;
        this.auditResult = auditResult;
        this.auditTime = auditTime;
    }
}
