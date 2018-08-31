package com.fekpal.dao.model;

import com.fekpal.common.base.BaseModel;

/**
 * Created by APone on 2018/2/5 19:16.
 */
public class LikeOrg extends BaseModel {

    private static final long serialVersionUID = -497770962000889055L;

    private Integer id;

    private Integer personId;

    private Integer orgId;

    /**
     * 默认为0，不点赞
     * 1 为点赞
     */
    private Integer available;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPersonId() {
        return personId;
    }

    public void setPersonId(Integer personId) {
        this.personId = personId;
    }

    public Integer getOrgId() {
        return orgId;
    }

    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
    }

    public Integer getAvailable() {
        return available;
    }

    public void setAvailable(Integer available) {
        this.available = available;
    }
}
