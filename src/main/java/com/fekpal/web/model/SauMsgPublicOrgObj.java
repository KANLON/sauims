package com.fekpal.web.model;

/**
 * 校社联信息发布对象类，用于校社联发布消息的时候获取的发布对象
 * @author zhangcanlong
 * @date 2018/4/5
 */
public class SauMsgPublicOrgObj {
    private int clubId;
    private String clubName;

    public SauMsgPublicOrgObj(){}
    public SauMsgPublicOrgObj(int clubId, String clubName) {
        this.clubId = clubId;
        this.clubName = clubName;
    }

    public int getClubId() {
        return clubId;
    }

    public String getClubName() {
        return clubName;
    }

    public void setClubId(int clubId) {
        this.clubId = clubId;
    }

    public void setClubName(String clubName) {
        this.clubName = clubName;
    }
}
