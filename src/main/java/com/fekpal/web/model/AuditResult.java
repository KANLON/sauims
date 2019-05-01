package com.fekpal.web.model;


import javax.validation.constraints.NotNull;

/**
 * 注册审核返回结果的类的
 * @author  zhangcanlong
 * @date 2018/4/7
 */
public class AuditResult {
    /**
     * 审核状态 auditState（0：驳回。1：通过。2：待审.3:删除）
     */
    @NotNull
    private Integer auditState;
    /**
     * 审核结果
     */
    @NotNull
    private String auditResult;

    public Integer getAuditState() {
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

    public AuditResult() {

    }

    public AuditResult(Integer auditState, String auditResult) {

        this.auditState = auditState;
        this.auditResult = auditResult;
    }
}
