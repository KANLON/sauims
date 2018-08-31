package com.fekpal.dao.model;

import com.fekpal.common.base.BaseModel;
import org.springframework.stereotype.Component;

/**
 * Created by APone on 2018/2/25 2:01.
 * 唯一注册信息封装
 * 既该字段的信息在数据库中是唯一的
 */
@Component
public class UniqueMsg extends BaseModel{

    private static final long serialVersionUID = 7855293806664844884L;

    private String userName;

    private String email;

    private String clubName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getClubName() {
        return clubName;
    }

    public void setClubName(String clubName) {
        this.clubName = clubName;
    }
}
