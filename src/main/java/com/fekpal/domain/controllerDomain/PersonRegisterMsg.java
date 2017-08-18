package com.fekpal.domain.controllerDomain;

/**
 * Created by hasee on 2017/8/18.
 */
public class PersonRegisterMsg {
    private String userName;
    private String password;
    private String captcha;
    public PersonRegisterMsg(){}

    @Override
    public String toString() {
        return "PersonRegisterMsg{" +
                "userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", captcha='" + captcha + '\'' +
                '}';
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCaptcha() {
        return captcha;
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }
}
