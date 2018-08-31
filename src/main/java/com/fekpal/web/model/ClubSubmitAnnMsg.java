package com.fekpal.web.model;

import com.fekpal.common.base.BaseModel;
import org.springframework.web.multipart.MultipartFile;

/**
 * 社团发送年度审核注册信息的类
 * @author zhangcanlong
 * @date 2018/4/7
 */
public class ClubSubmitAnnMsg extends BaseModel {
    /**
     * 标题
     */
    private  String auditTitle;
    /**
     * 内容描述
     */
    private String description;

    /**
     * 申请文件MultipartFile
     */
    private MultipartFile annAuditFile;

    public String getAuditTitle() {
        return auditTitle;
    }

    public void setAuditTitle(String auditTitle) {
        this.auditTitle = auditTitle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public MultipartFile getAnnAuditFile() {
        return annAuditFile;
    }

    public void setAnnAuditFile(MultipartFile annAuditFile) {
        this.annAuditFile = annAuditFile;
    }

    public ClubSubmitAnnMsg(String auditTitle, String description, MultipartFile annAuditFile) {

        this.auditTitle = auditTitle;
        this.description = description;
        this.annAuditFile = annAuditFile;
    }

    public ClubSubmitAnnMsg() {

    }

}
