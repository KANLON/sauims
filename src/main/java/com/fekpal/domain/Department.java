package com.fekpal.domain;


public class Department extends BasePOJO {

    private int id;

    private String departmentName;

    private int departmentAvailable;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

}
