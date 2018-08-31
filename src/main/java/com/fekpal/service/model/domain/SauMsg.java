package com.fekpal.service.model.domain;

import com.fekpal.common.base.BaseModel;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;


/**
 * Created by APone on 2018/2/23 16:41.
 * 校社联信息修改封装
 */
public class SauMsg extends BaseModel {

    private static final long serialVersionUID = -8443026982283318374L;

    private String sauName;

    private Timestamp foundTime;

    private String description;

    private String adminName;

    private MultipartFile logo;

    private MultipartFile view;

    public void setView(MultipartFile view) {
        this.view = view;
    }

    public MultipartFile getView() {

        return view;
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

    public MultipartFile getLogo() {
        return logo;
    }

    public void setLogo(MultipartFile logo) {
        this.logo = logo;
    }
}
