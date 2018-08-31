package com.fekpal.web.model;

/**
 * 发送删除消息id模型参数
 */
public class DeleteMsgIdsModel {

    /**
     * 前端传过来的参数
     */
    private String deleteMsgIds;

    public DeleteMsgIdsModel() {
    }

    public void setDeleteMsgIds(String deleteMsgIds) {
        this.deleteMsgIds = deleteMsgIds;
    }

    public String getDeleteMsgIds() {

        return deleteMsgIds;
    }
}
