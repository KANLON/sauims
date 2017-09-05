package com.fekpal.domain;

import java.util.List;

public class Major extends BasePOJO {

    private int majorId;

    private String majorName;

    private int majorAvailable;

    public int getMajorId() {
        return majorId;
    }

    public void setMajorId(int majorId) {
        this.majorId = majorId;
    }

    public String getMajorName() {
        return majorName;
    }

    public void setMajorName(String majorName) {
        this.majorName = majorName;
    }

    public int getMajorAvailable() {
        return majorAvailable;
    }

    public void setMajorAvailable(int majorAvailable) {
        this.majorAvailable = majorAvailable;
    }


}
