package com.fekpal.web.model;

import org.springframework.stereotype.Component;

/**
 * Created by APone on 2018/3/4 15:16.
 */
@Component
public class SearchPage extends PageList {

    private static final long serialVersionUID = -4855087708286906790L;

    private String findContent;

    public SearchPage() {
        super();
        this.findContent = "";
    }

    public String getFindContent() {
        return findContent;
    }

    public void setFindContent(String findContent) {
        this.findContent = findContent;
    }
}
