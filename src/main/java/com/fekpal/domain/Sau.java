package com.fekpal.domain;

import java.sql.Timestamp;

public class Sau extends BasePOJO {

    private int sauId;

    private int userId;

    private String sauName;

    private Timestamp foundTime;

    private String description;

    private String adminName;

    private String sauLogo;

    private int members;

    public int getSauId() {
        return sauId;
    }

    public void setSauId(int sauId) {
        this.sauId = sauId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getSauName() {
        return sauName;
    }

    public void setSauName(String sauName) {
        this.sauName = sauName;
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

    public String getSauLogo() {
        return sauLogo;
    }

    public void setSauLogo(String sauLogo) {
        this.sauLogo = sauLogo;
    }

    public int getMembers() {
        return members;
    }

    public void setMembers(int members) {
        this.members = members;
    }
}
