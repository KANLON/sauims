package com.fekpal.web.model;


import com.fekpal.common.base.BaseModel;
import org.springframework.stereotype.Component;

/**
 * 用来发送给前端的社团列表信息实体类
 * Created by hasee on 2017/8/15.
 */
@Component
public class OrgListMsg extends BaseModel {

    private static final long serialVersionUID = 4994884684828642007L;

    private String orgName;

    private Integer orgId;

    private String description;

    private String view;

    private String logo;

    private Integer members;

    private Integer likeClick;

    private int isClick;

    public void setOrgId(int orgId) {
        this.orgId = orgId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getIsClick() {
        return isClick;
    }

    public void setIsClick(int isClick) {
        this.isClick = isClick;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public Integer getOrgId() {
        return orgId;
    }

    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
    }

    public String getView() {
        return view;
    }

    public void setView(String view) {
        this.view = view;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public Integer getMembers() {
        return members;
    }

    public void setMembers(Integer members) {
        this.members = members;
    }

    public Integer getLikeClick() {
        return likeClick;
    }

    public void setLikeClick(Integer likeClick) {
        this.likeClick = likeClick;
    }
}
