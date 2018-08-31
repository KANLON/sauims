package com.fekpal.web.model;

import java.util.Date;

/**
 * 查看校社联成员加入校社联的详细信息类
 * @author zhangcanlong
 * @date 2018/4/7
 */
public class PersonJoinAuditDetail {
    /**
     * 审核id
     */
    private int auditId;
    /**
     *  用户名
     */
    private String userName;
    /**
     * 注册时间
     */
    private Date registerTime;
    /**
     * 个人头像
     */
    private String personLogo;
    /**
     * 真实姓名
     */
    private String realName;
    /**
     * 学号
     */
    private String studentId;
    /**
     * 性别
     */
    private String gender;
    /**
     * 生日
     */
    private Date birthday;
    /**
     * 手机号
     */
    private String phone;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 所属系
     */
    private String departmentName;
    /**
     * 所属专业
     */
    private String majorName;
    /**
     * 宿舍地址
     */
    private String address;

    /**
     * 审核状态 auditState（0：驳回。1：通过。2：待审.3：删除）
     */
    private int auditSate;

    /**
     * 审核结果a
     */
    private String auditResult;

    public int getAuditSate() {
        return auditSate;
    }

    public void setAuditSate(int auditSate) {
        this.auditSate = auditSate;
    }

    public String getAuditResult() {
        return auditResult;
    }

    public void setAuditResult(String auditResult) {
        this.auditResult = auditResult;
    }

    public int getAuditId() {
        return auditId;
    }

    public void setAuditId(int auditId) {
        this.auditId = auditId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Date getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(Date registerTime) {
        this.registerTime = registerTime;
    }

    public String getPersonLogo() {
        return personLogo;
    }

    public void setPersonLogo(String personLogo) {
        this.personLogo = personLogo;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getMajorName() {
        return majorName;
    }

    public void setMajorName(String majorName) {
        this.majorName = majorName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public PersonJoinAuditDetail() {

    }

    public PersonJoinAuditDetail(int auditId, String userName, Date registerTime, String personLogo, String realName, String studentId, String gender, Date birthday, String phone, String email, String departmentName, String majorName, String address, int auditSate, String auditResult) {
        this.auditId = auditId;
        this.userName = userName;
        this.registerTime = registerTime;
        this.personLogo = personLogo;
        this.realName = realName;
        this.studentId = studentId;
        this.gender = gender;
        this.birthday = birthday;
        this.phone = phone;
        this.email = email;
        this.departmentName = departmentName;
        this.majorName = majorName;
        this.address = address;
        this.auditSate = auditSate;
        this.auditResult = auditResult;
    }
}
