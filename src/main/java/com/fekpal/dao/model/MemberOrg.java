package com.fekpal.dao.model;


import java.sql.Timestamp;

/**
 * Created by APone on 2018/2/26 20:27.
 */
public class MemberOrg extends Org {

    private static final long serialVersionUID = 6300840844736636054L;

    private Integer id;

    private Integer personId;

    private Integer memberDuty;

    /**
     * 默认状态是在部门里面
     */
    private Integer memberState=1;

    private Timestamp joinTime;

    private Timestamp leaveTime;

    /**
     * 默认部分为无
     */
    private String orgDepartment="无";

    private Integer available;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
