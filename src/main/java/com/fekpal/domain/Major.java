package com.fekpal.domain;

public class Major extends BasePOJO {

    private int majorId;

    private String majorName;

    private Department department;

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

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public int getMajorAvailable() {
        return majorAvailable;
    }

    public void setMajorAvailable(int majorAvailable) {
        this.majorAvailable = majorAvailable;
    }
}
