package com.fekpal.service.model.domain;

import com.fekpal.common.base.BaseModel;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;

/**
 * Created by APone on 2018/2/23 16:50.
 */
public class ClubMsg extends BaseModel{

    private static final long serialVersionUID = 8197472920074309865L;

    private String clubName;

    private MultipartFile logo;

    private MultipartFile view;

    private String description;

    private String adminName;

    private Timestamp foundTime;

    private String orgType;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public ClubMsg() {
    }

    public String getOrgType() {
        return orgType;

    }

    public void setOrgType(String orgType) {
        this.orgType = orgType;
    }

    public String getClubName() {
        return clubName;
    }

    public void setClubName(String clubName) {
        this.clubName = clubName;
    }

    public MultipartFile getLogo() {
        return logo;
    }

    public void setLogo(MultipartFile logo) {
        this.logo = logo;
    }

    public MultipartFile getView() {
        return view;
    }

    public void setView(MultipartFile view) {
        this.view = view;
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

    public Timestamp getFoundTime() {
        return foundTime;
    }

    public void setFoundTime(Timestamp foundTime) {
        this.foundTime = foundTime;
    }
}
