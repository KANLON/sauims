package com.fekpal.web.model;

import java.util.Date;

/**
 * 校社联审核社团注册信息的详细内容
 * @author  zhangcanlong
 * @date 2018/4/7
 */
public class SauClubAuditDetail {
    /**
     * 审核id
     */
    private int audtiMsgId;
    /**
     * 真实姓名
     */
    private String realName;
    /**
     * 注册时间
     */
    private Date registerTime;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 手机号
     */
    private String phone;
    /**
     * 社团名称
     */
    private String clubName;
    /**
     * 社团种类
     */
    private String clubType;
    /**
     * 社团描述
     */
    private String description;
    /**
     * 申请文件
     */
    private String file;
    /**
     * 审核状态 auditState（0：驳回。1：通过。2：待审.3：删除）
     */
    private int auditSate;

    /**
     * 审核结果a
     */
    private String auditResult;

    public int getAuditSate() {
        return auditSate;
    }

    public void setAuditSate(int auditSate) {
        this.auditSate = auditSate;
    }

    public String getAuditResult() {
        return auditResult;
    }

    public void setAuditResult(String auditResult) {
        this.auditResult = auditResult;
    }



    public int getAudtiMsgId() {
        return audtiMsgId;
    }

    public void setAudtiMsgId(int audtiMsgId) {
        this.audtiMsgId = audtiMsgId;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public Date getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(Date registerTime) {
        this.registerTime = registerTime;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getClubName() {
        return clubName;
    }

    public void setClubName(String clubName) {
        this.clubName = clubName;
    }

    public String getClubType() {
        return clubType;
    }

    public void setClubType(String clubType) {
        this.clubType = clubType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public SauClubAuditDetail() {

    }

    public SauClubAuditDetail(int audtiMsgId, String realName, Date registerTime, String email, String phone, String clubName, String clubType, String description, String file) {

        this.audtiMsgId = audtiMsgId;
        this.realName = realName;
        this.registerTime = registerTime;
        this.email = email;
        this.phone = phone;
        this.clubName = clubName;
        this.clubType = clubType;
        this.description = description;
        this.file = file;
    }
}
