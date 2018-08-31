package com.fekpal.dao.model;

import com.fekpal.common.base.BaseModel;
import com.fekpal.common.constant.AuditState;

import java.sql.Timestamp;

/**
 * Created by APone on 2018/3/6 21:50.
 */
public class Member extends BaseModel {

    private static final long serialVersionUID = -1989466091093519928L;

    private Integer id;

    private Integer orgId;

    private Integer personId;

    private Integer memberDuty;

    /**
     * 离开状态（0，离开。1.还在该社团）
     */
    private Integer memberState;

    private Timestamp joinTime;

    private Timestamp leaveTime;

    private String orgDepartment;
    /**
     * 审核状态（0，拒绝。1：通过。2：审核中。3：删除），用户社团审核状态
     */
    private Integer available;

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

    public Integer getPersonId() {
        return personId;
    }

    public void setPersonId(Integer personId) {
        this.personId = personId;
    }

    public Integer getMemberDuty() {
        return memberDuty;
    }

    public void setMemberDuty(Integer memberDuty) {
        this.memberDuty = memberDuty;
    }

    public Integer getMemberState() {
        return memberState;
    }

    public void setMemberState(Integer memberState) {
        this.memberState = memberState;
    }

    public Timestamp getJoinTime() {
        return joinTime;
    }

    public void setJoinTime(Timestamp joinTime) {
        this.joinTime = joinTime;
    }

    public Timestamp getLeaveTime() {
        return leaveTime;
    }

    public void setLeaveTime(Timestamp leaveTime) {
        this.leaveTime = leaveTime;
    }

    public String getOrgDepartment() {
        return orgDepartment;
    }

    public void setOrgDepartment(String orgDepartment) {
        this.orgDepartment = orgDepartment;
    }

    public Integer getAvailable() {
        return available;
    }

    public void setAvailable(Integer available) {
        this.available = available;
    }
}
