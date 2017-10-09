package com.fekpal.domain.controllerDomain;

/**
 * 用来发送给前端的社团列表信息实体类
 * Created by hasee on 2017/8/15.
 */
public class ClubListMsg {
    //社团ID
    private int clubId;
    //社团名字
    private String clubName;
    //社团展示的图片名称
    private String clubView;
    //社团描述
    private  String description;
    //社团成员
    private int members;
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

    public Integer getMembers() {
        return members;
    }

    public void setMembers(Integer members) {
        this.members = members;
    }

    public Integer getLikeNumber() {
        return likeNumber;
    }

    public void setLikeNumber(Integer likeNumber) {
        this.likeNumber = likeNumber;
    }

    public void setClubId(int clubId) {
        this.clubId = clubId;
    }

    public void setClubName(String clubName) {
        this.clubName = clubName;
    }

    public void setMembers(int members) {
        this.members = members;
    }

    public void setLikeNumber(int likeNumber) {
        this.likeNumber = likeNumber;
    }

    public String getClubName() {

        return clubName;
    }
}
