package com.fekpal.domain;

import java.util.List;

public class Authority extends BasePOJO {

    private int authorityId;

    private int authorityAvailable;

    private String authorityName;

    private List<Resource> resourceList;

    public Authority() {
    }

    public int getAuthorityId() {
        return authorityId;
    }

    public void setAuthorityId(int authorityId) {
        this.authorityId = authorityId;
    }

    public String getAuthorityName() {
        return authorityName;
    }

    public void setAuthorityName(String authorityName) {
        this.authorityName = authorityName;
    }

    public int getAuthorityAvailable() {
        return authorityAvailable;
    }

    public void setAuthorityAvailable(int authorityAvailable) {
        this.authorityAvailable = authorityAvailable;
    }


    public List<Resource> getResourceList() {
        return resourceList;
    }

    public void setResourceList(List<Resource> resourceList) {
        this.resourceList = resourceList;
    }
}
