package com.fekpal.dao.model;

import java.util.List;

public class MessageReceive extends Message {

    private static final long serialVersionUID = 7974631739775373072L;

    private Integer id;

    private Integer receiveId;

    private Integer available;

    private Integer readFlag;

    private List<Integer> receives;

    public List<Integer> getReceives() {
        return receives;
    }

    public void setReceives(List<Integer> receives) {
        this.receives = receives;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getReceiveId() {
        return receiveId;
    }

    public void setReceiveId(Integer receiveId) {
        this.receiveId = receiveId;
    }

    public Integer getAvailable() {
        return available;
    }

    public void setAvailable(Integer available) {
        this.available = available;
    }

    public Integer getReadFlag() {
        return readFlag;
    }

    public void setReadFlag(Integer readFlag) {
        this.readFlag = readFlag;
    }
}
