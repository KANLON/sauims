package com.fekpal.web.model;

import com.fekpal.common.base.BaseModel;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

/**
 * Created by APone on 2018/3/4 14:24.
 * @author APone
 */
@Component
public class OrgDetail extends BaseModel {

    private static final long serialVersionUID = -8999216384274349374L;

    private int orgId;

    private String orgName;

    private Timestamp foundTime;

    private String description;

    private String adminName;

    private String orgType;

    private Integer likeClick;

    private Integer members;

    private String view;

    private String logo;

    private String email;

    private String phone;

    /**
     * 口号
     */
    private String headIntroduce;

    /**
     * 社团内男生数量
     */
    private Integer manNum;
    /**
     * 社团内女生数量
     */
    private Integer womanNum;
    /**
     * 社团内大一，即一年级学生的数量
     */
    private Integer firstGradeNum;
    /**
     * 社团内大二，即二年级学生的数量
     */
    private Integer secondGradeNum;
    /**
     * 社团内大三，即三年级学生的数量
     */
    private Integer threeGradeNum;
    /**
     * 社团内大四，即四年级学生的数量
     */
    private Integer fourGradeNum;
    /**
     * 社团内已经毕业的学生的数量
     */
    private Integer graduatedNum;

    public String getHeadIntroduce() {
        return headIntroduce;
    }

    public void setHeadIntroduce(String headIntroduce) {
        this.headIntroduce = headIntroduce;
    }

    public Integer getManNum() {
        return manNum;
    }

    public void setManNum(Integer manNum) {
        this.manNum = manNum;
    }

    public Integer getWomanNum() {
        return womanNum;
    }

    public void setWomanNum(Integer womanNum) {
        this.womanNum = womanNum;
    }

    public Integer getFirstGradeNum() {
        return firstGradeNum;
    }

    public void setFirstGradeNum(Integer firstGradeNum) {
        this.firstGradeNum = firstGradeNum;
    }

    public Integer getSecondGradeNum() {
        return secondGradeNum;
    }

    public void setSecondGradeNum(Integer secondGradeNum) {
        this.secondGradeNum = secondGradeNum;
    }

    public Integer getThreeGradeNum() {
        return threeGradeNum;
    }

    public void setThreeGradeNum(Integer threeGradeNum) {
        this.threeGradeNum = threeGradeNum;
    }

    public Integer getFourGradeNum() {
        return fourGradeNum;
    }

    public void setFourGradeNum(Integer fourGradeNum) {
        this.fourGradeNum = fourGradeNum;
    }

    public Integer getGraduatedNum() {
        return graduatedNum;
    }

    public void setGraduatedNum(Integer graduatedNum) {
        this.graduatedNum = graduatedNum;
    }

    private int joinState;

    public String getView() {
        return view;
    }

    public void setView(String view) {
        this.view = view;
    }

    public int getOrgId() {
        return orgId;
    }

    public void setOrgId(int orgId) {
        this.orgId = orgId;
    }

    public int getJoinState() {
        return joinState;
    }

    public void setJoinState(int joinState) {
        this.joinState = joinState;
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

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
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
}
