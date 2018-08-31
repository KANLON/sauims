package com.fekpal.web.model;

import com.fekpal.common.base.BaseModel;
import org.springframework.stereotype.Component;

/**
 * Created by APone on 2018/2/11 23:44.
 * 验证来源信息封装
 */
@Component
public class CaptchaMsg extends BaseModel {

    private static final long serialVersionUID = -5865592835151143902L;

    private String phone;

    private String email;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
