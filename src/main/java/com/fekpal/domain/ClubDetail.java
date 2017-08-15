package com.fekpal.domain;

import java.util.Date;

/**
 * 这是社团详细信息的实体类
 * Created by hasee on 2017/8/15.
 */
public class ClubDetail {
    private int clubId;
    private String clubName;
    private String clubLogo;
    private String description;
    private String adminName;
    private String email;
    private String phone;
    private Date foundTime;
    private int menbers;
    public ClubDetail(){}

    public int getClubId() {
        return clubId;
    }

    public void setClubId(int clubId) {
        this.clubId = clubId;
    }

    public String getClubName() {
        return clubName;
    }

    public void setClubName(String clubName) {
        this.clubName = clubName;
    }

    public String getClubLogo() {
        return clubLogo;
    }

    public void setClubLogo(String clubLogo) {
        this.clubLogo = clubLogo;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getFoundTime() {
        return foundTime;
    }

    public void setFoundTime(Date foundTime) {
        this.foundTime = foundTime;
    }

    public int getMenbers() {
        return menbers;
    }

    public void setMenbers(int menbers) {
        this.menbers = menbers;
    }
}
