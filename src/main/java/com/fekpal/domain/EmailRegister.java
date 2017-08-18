package com.fekpal.domain;

/**
 * 用来接收前端发送过来的邮箱 email的类
 * Created by hasee on 2017/8/18.
 */
public class EmailRegister {
    private String email;

    public EmailRegister(){}
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
