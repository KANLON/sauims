package com.fekpal.dao.model;

import com.fekpal.common.base.BaseModel;

import java.sql.Timestamp;

public class AnniversaryAudit extends BaseModel {

    private static final long serialVersionUID = -2315766494340261103L;

    /**
     * 自增id
     */
    private Integer id;

    /**
     * 提交的组织id
     */
    private Integer orgId;

    /**
     * 审核标题 varchar
     */
    private String auditTitle;

    /**
     * 文件名称 varchar（.docx）
     */
    private String fileName;

    /**
     * 审核状态 0:否决，1，通过，2，待审（暂定：3，删除）
     */
    private Integer auditState;

    private Timestamp auditTime;

    private String auditResult;

    private Timestamp submitTime;

    private String submitDescription;

    public String getAuditTitle() {
        return auditTitle;
    }

    public void setAuditTitle(String auditTitle) {
        this.auditTitle = auditTitle;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOrgId() {
        return orgId;
    }

    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Integer getAuditState() {
        return auditState;
    }

    public void setAuditState(Integer auditState) {
        this.auditState = auditState;
    }

    public Timestamp getAuditTime() {
        return auditTime;
    }

    public void setAuditTime(Timestamp auditTime) {
        this.auditTime = auditTime;
    }

    public String getAuditResult() {
        return auditResult;
    }

    public void setAuditResult(String auditResult) {
        this.auditResult = auditResult;
    }

    public Timestamp getSubmitTime() {
        return submitTime;
    }

    public void setSubmitTime(Timestamp submitTime) {
        this.submitTime = submitTime;
    }

    public String getSubmitDescription() {
        return submitDescription;
    }

    public void setSubmitDescription(String submitDescription) {
        this.submitDescription = submitDescription;
    }
}
