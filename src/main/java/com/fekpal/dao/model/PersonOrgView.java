package com.fekpal.dao.model;

/**
 * Created by APone on 2018/3/4 20:14.
 */
public class PersonOrgView extends Org {

    private static final long serialVersionUID = -3270267110110400581L;

    private int joinState;

    public int getJoinState() {
        return joinState;
    }

    public void setJoinState(int joinState) {
        this.joinState = joinState;
    }
}
