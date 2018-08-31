package com.fekpal.web.model;

/**
 * 社团发送自定义消息的发送对象，就是社团内的社员
 * @author  zhangcanlong
 * @date 2018/4/9
 */
public class ClubCustomSendObj {

    /**
     * 用户Id
     */
    private int userId;
    /**
     * 用户真实名字
     */
    private String realName;

    public ClubCustomSendObj() {
    }

    public ClubCustomSendObj(int userId, String realName) {

        this.userId = userId;
        this.realName = realName;
    }

    public int getUserId() {

        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }
}
