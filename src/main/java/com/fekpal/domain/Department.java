package com.fekpal.domain;


import java.util.List;

public class Department extends BasePOJO {

    private int departmentId;

    private String departmentName;

    private int departmentAvailable;

    private List<Major> majorList;

    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public int getDepartmentAvailable() {
        return departmentAvailable;
    }

    public void setDepartmentAvailable(int departmentAvailable) {
        this.departmentAvailable = departmentAvailable;
    }

    public List<Major> getMajorList() {
        return majorList;
    }

    public void setMajorList(List<Major> majorList) {
        this.majorList = majorList;
    }

}
