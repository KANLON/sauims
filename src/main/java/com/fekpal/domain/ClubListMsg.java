package com.fekpal.domain;

/**
 * 用来发送给前端的社团列表信息实体类
 * Created by hasee on 2017/8/15.
 */
public class ClubListMsg {
    //社团ID
    private int clubId;
    //社团展示的图片名称
    private String clubView;
    //社团描述
    private  String description;
    //社团成员
    private int menbers;
    //喜爱人数
    private int likeNumber;

    public ClubListMsg(){}

    public Integer getClubId() {
        return clubId;
    }

    public void setClubId(Integer clubId) {
        this.clubId = clubId;
    }

    public String getClubView() {
        return clubView;
    }

    public void setClubView(String clubView) {
        this.clubView = clubView;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getMenbers() {
        return menbers;
    }

    public void setMenbers(Integer menbers) {
        this.menbers = menbers;
    }

    public Integer getLikeNumber() {
        return likeNumber;
    }

    public void setLikeNumber(Integer likeNumber) {
        this.likeNumber = likeNumber;
    }
}
