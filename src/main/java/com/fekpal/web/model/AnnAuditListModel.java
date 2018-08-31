package com.fekpal.web.model;

import java.util.Date;

/**
 * 年度审核列表的类模型，用于传递给前端获取年度审核列表信息
 * @author zhangcanlong
 * @date 2018/4/6
 */
public class AnnAuditListModel {
    /**
     * 审核消息ID
     */
    private int auditMsgId;
    /**
     *  审核标题 以年份加社团名
     */
    private String registerTitle;
    /**
     * 注册人
     */
    private String registerName;
    /**
     * 注册时间
     */
    private Date registerTime;
    /**
     * 审核状态auditState（0，否决，1：通过；2；待审）
     */
    private int auditState;

    public int getAuditMsgId() {
        return auditMsgId;
    }

    public void setAuditMsgId(int auditMsgId) {
        this.auditMsgId = auditMsgId;
    }

    public String getRegisterTitle() {
        return registerTitle;
    }

    public void setRegisterTitle(String registerTitle) {
        this.registerTitle = registerTitle;
    }

    public String getRegisterName() {
        return registerName;
    }

    public void setRegisterName(String registerName) {
        this.registerName = registerName;
    }

    public Date getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(Date registerTime) {
        this.registerTime = registerTime;
    }

    public int getAuditState() {
        return auditState;
    }

    public void setAuditState(int auditState) {
        this.auditState = auditState;
    }

    public AnnAuditListModel() {

    }

    public AnnAuditListModel(int auditMsgId, String registerTitle, String registerName, Date registerTime, int auditState) {

        this.auditMsgId = auditMsgId;
        this.registerTitle = registerTitle;
        this.registerName = registerName;
        this.registerTime = registerTime;
        this.auditState = auditState;
    }
}
