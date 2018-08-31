package com.fekpal.web.model;

import java.util.Date;

/**
 * 社团管理端，注册审核的列表信息类
 * @author zhangcanlong
 * @date 2018/4/8
 */
public class ClubRegisterAuditListMsg {
//    审核信息ID auditMsgId
//    审核标题auditTitle
//     注册时间registerTime
//    审核状态auditState（0，否决，1：通过；2；待审）
    /**
     * 审核信息ID
     */
    private int auditMsgId;
    /**
     * 审核标题
     */
    private String auditTitle;
    /**
     * 注册时间r
     */
    private Date registerTime;
    /**
     * 审核状态auditState（0，否决，1：通过；2；待审;3：删除）
     */
    private int auditState;

    public ClubRegisterAuditListMsg(int auditMsgId, String auditTitle, Date registerTime, int auditState) {
        this.auditMsgId = auditMsgId;
        this.auditTitle = auditTitle;
        this.registerTime = registerTime;
        this.auditState = auditState;
    }

    public ClubRegisterAuditListMsg() {

    }

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
}
