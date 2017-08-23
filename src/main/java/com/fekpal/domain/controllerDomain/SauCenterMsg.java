package com.fekpal.domain.controllerDomain;

import java.util.Date;

/**
 * 校社联中心信息的实体（目前这个类没有用，先留着）
 * Created by hasee on 2017/8/20.
 */
public class SauCenterMsg {
    private String clubName ;
    private String description ;
    private String adminName ;
    private String email ;
    private String captcha ;
    private Date foundTime;

    public SauCenterMsg(){

    }


    public String getClubName() {
        return clubName;
    }

    public void setClubName(String clubName) {
        this.clubName = clubName;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCaptcha() {
        return captcha;
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }

    public Date getFoundTime() {
        return foundTime;
    }

    public void setFoundTime(Date foundTime) {
        this.foundTime = foundTime;
    }

    @Override
    public String toString() {
        return "SauCenterMsg{" +
                "clubName='" + clubName + '\'' +
                ", description='" + description + '\'' +
                ", adminName='" + adminName + '\'' +
                ", email='" + email + '\'' +
                ", captcha='" + captcha + '\'' +
                ", foundTime=" + foundTime +
                '}';
    }
}
