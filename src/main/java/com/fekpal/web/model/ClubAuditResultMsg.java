package com.fekpal.web.model;

/**
 * 审核社团注册的结果信息类，存放是否通过审核的信息
 * @author zhangcanlong
 * @date 2018/4/7
 */
public class ClubAuditResultMsg {
    /**
     *  审核状态 （0：驳回。1：通过。2：待审。3：删除）
     */
    private int auditState;
    /**
     * 审核结果
     */
    private String auditResult;

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

    public ClubAuditResultMsg() {

    }

    public ClubAuditResultMsg(int auditState, String auditResult) {

        this.auditState = auditState;
        this.auditResult = auditResult;
    }
}
