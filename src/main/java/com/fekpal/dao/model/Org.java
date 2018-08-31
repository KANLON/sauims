package com.fekpal.dao.model;

import com.fekpal.common.base.BaseModel;

import java.sql.Timestamp;

/**
 * Created by APone on 2018/2/27 19:39.
 */
public class Org extends BaseModel{
    
    private static final long serialVersionUID = -3403405786128549267L;

    private Integer orgId;

    private Integer userId;

    private String orgName;

    private Timestamp foundTime;

    private String description;

    private String adminName;

    private String orgType;

    private String orgView;

    private Integer likeClick;

    private Integer members;

    private Integer orgState;

    private Integer orgAuth;

    private String orgLogo;

    private String contactEmail;

    private String contactNumber;

    public Integer getOrgId() {
        return orgId;
    }

    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public Timestamp getFoundTime() {
        return foundTime;
    }

    public void setFoundTime(Timestamp foundTime) {
        this.foundTime = foundTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public String getOrgType() {
        return orgType;
    }

    public void setOrgType(String orgType) {
        this.orgType = orgType;
    }

    public String getOrgView() {
        return orgView;
    }

    public void setOrgView(String orgView) {
        this.orgView = orgView;
    }

    public Integer getLikeClick() {
        return likeClick;
    }

    public void setLikeClick(Integer likeClick) {
        this.likeClick = likeClick;
    }

    public Integer getMembers() {
        return members;
    }

    public void setMembers(Integer members) {
        this.members = members;
    }

    public Integer getOrgState() {
        return orgState;
    }

    public void setOrgState(Integer orgState) {
        this.orgState = orgState;
    }

    public Integer getOrgAuth() {
        return orgAuth;
    }

    public void setOrgAuth(Integer orgAuth) {
        this.orgAuth = orgAuth;
    }

    public String getOrgLogo() {
        return orgLogo;
    }

    public void setOrgLogo(String orgLogo) {
        this.orgLogo = orgLogo;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }
}
