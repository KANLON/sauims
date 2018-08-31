package com.fekpal.web.model;

import java.util.Date;

/**
 * 校社联注册审核信息列表类
 * @author zhangcanlong
 * @date 2018/8/19
 */
public class SauRegAudit {

    /**
     * 审核信息ID
     */
    private int auditMsgId;
    /**
     * 审核标题(注册社联成员真实姓名或者注册社团名)
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
     * 角色role（0表示注册校社联组织成员，1表示注册社团）
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
}
