package com.fekpal.dao.model;

import com.fekpal.common.base.BaseModel;

import java.sql.Timestamp;

/**
 * 校社联审核社团和校社联成员的注册
 * @author zhangcanlong
 * @date 2018/7/9
 */
public class SauAuditRegister extends BaseModel {

    private static final long serialVersionUID = 7855559872220869969L;
    /**
     * 审核消息的id
     */
    private Integer auditMsgId;
    /**
     * 审核消息的标题（是社团名或者校社联内成员名）
     */
    private String auditTitle;
    /**
     * 注册人的姓名
     */
    private String registerName;
    /**
     * 注册时间
     */
    private Timestamp registerTime;
    /**
     * 审核状态，0为审核为通过，1为审核通过，2为正在审核
     */
    private Integer auditState;
    /**
     * 审核的角色，是审核社团还是审核校社联内成员，审核社团内成员则为0,审核社团则为1，
     */
    private Integer role;

    public Integer getAuditMsgId() {
        return auditMsgId;
    }

    public void setAuditMsgId(Integer auditMsgId) {
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

    public Timestamp getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(Timestamp registerTime) {
        this.registerTime = registerTime;
    }

    public Integer getAuditState() {
        return auditState;
    }

    public void setAuditState(Integer auditState) {
        this.auditState = auditState;
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }
}
