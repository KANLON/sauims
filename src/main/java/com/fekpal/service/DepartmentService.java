package com.fekpal.service;

import com.fekpal.domain.Department;

import java.util.List;

/**
 * Created by APone on 2017/9/5.
 * DepartmentService接口
 */
public interface DepartmentService {

    /**
     * 根据系别id获得系部
     *
     * @param departmentId int
     * @return Department
     */
    Department getDepartmentByDepartmentId(int departmentId);

    /**
     * 获取所有的系别
     *
     * @return List
     */
    List<Department> loadAllDepartment();

}
