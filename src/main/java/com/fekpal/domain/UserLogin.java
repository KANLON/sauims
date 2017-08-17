package com.fekpal.domain;

import org.springframework.stereotype.Component;

/**
 * 用来封装登陆发送的数据的用户登陆类
 * Created by hasee on 2017/8/16.
 */
@Component
public class UserLogin {
    private String userName;
    private String password;
    //验证码
    private String captcha;

    public UserLogin(){}
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

    public String getCaptcha() {
        return captcha;
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }

    @Override
    public String toString() {
        return "UserLogin{" +
                "userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", captcha='" + captcha + '\'' +
                '}';
    }
}
