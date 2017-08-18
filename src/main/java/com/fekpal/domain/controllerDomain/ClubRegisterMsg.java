package com.fekpal.domain.controllerDomain;

/**
 * 社团注册信息的实体类
 * Created by hasee on 2017/8/18.
 */
public class ClubRegisterMsg {
    private String userName;
    private String password;
    private String realName;
    private String email;
    //邮箱验证码
    private String captcha;
    private String phone;
    private String clubName;
    private String clubType;
    private String description;
    private String file;
    public ClubRegisterMsg(){}

    @Override
    public String toString() {
        return "ClubRegisterMsg{" +
                "userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", realName='" + realName + '\'' +
                ", email='" + email + '\'' +
                ", captcha='" + captcha + '\'' +
                ", phone='" + phone + '\'' +
                ", clubName='" + clubName + '\'' +
                ", clubType='" + clubType + '\'' +
                ", description='" + description + '\'' +
                ", file='" + file + '\'' +
                '}';
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email.trim();
    }

    public String getCaptcha() {
        return captcha;
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getClubName() {
        return clubName;
    }

    public void setClubName(String clubName) {
        this.clubName = clubName;
    }

    public String getClubType() {
        return clubType;
    }

    public void setClubType(String clubType) {
        this.clubType = clubType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }
}
