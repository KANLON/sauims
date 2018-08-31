package com.fekpal.web.model;

import java.util.Date;

/**
 * 社团审核的列表的信息类
 * @author  zhangcanlong
 * @date 2018/4/7
 */
public class ClubAuditListMsg {
    /**
     * 审核信息ID
     */
    private int auditMsgId;
    /**
     * 审核标题a
     */
    private String auditTitle;
    /**
     * 注册人
     */
    private String registerName;
    /**
     * 注册时间
     */
    private Date registerTime;
    /**
     * 审核状态auditState（0，否决，1：通过；2；待审;3删除）
     */
    private int auditState;
    /**
     * 角色role（社团注册审核没有该字段没有）
     */
    private int role;

    public int getAuditMsgId() {
        return auditMsgId;
    }

    public void setAuditMsgId(int auditMsgId) {
        this.auditMsgId = auditMsgId;
    }

    public String getAuditTitle() {
        return auditTitle;
    }

    public void setAuditTitle(String auditTitle) {
        this.auditTitle = auditTitle;
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

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public ClubAuditListMsg() {

    }

    public ClubAuditListMsg(int auditMsgId, String auditTitle, String registerName, Date registerTime, int auditState, int role) {

        this.auditMsgId = auditMsgId;
        this.auditTitle = auditTitle;
        this.registerName = registerName;
        this.registerTime = registerTime;
        this.auditState = auditState;
        this.role = role;
    }
}
