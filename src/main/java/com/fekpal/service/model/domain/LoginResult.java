package com.fekpal.service.model.domain;

import com.fekpal.common.base.BaseModel;

/**
 * Created by APone on 2018/2/22 13:55.
 * 登录结果封装
 */
public class LoginResult extends BaseModel{

    private static final long serialVersionUID = -3640349510026355851L;

    /**
     * 登录结果
     */
    private Integer resultState;

    /**
     * 用户权限
     */
    private Integer authority;

    public Integer getResultState() {
        return resultState;
    }

    public void setResultState(Integer resultState) {
        this.resultState = resultState;
    }

    public Integer getAuthority() {
        return authority;
    }

    public void setAuthority(Integer authority) {
        this.authority = authority;
    }
}
