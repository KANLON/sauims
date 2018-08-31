package com.fekpal.api;

import com.fekpal.common.base.BaseService;
import com.fekpal.dao.model.ClubAudit;
import com.fekpal.web.model.ClubAuditResultMsg;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by APone on 2017/9/5.
 * ClubAuditService接口
 * 该接口用于校社联审核关社团注册申请的审核等操作
 * @author APone
 */
public interface ClubAuditService extends BaseService<ClubAudit> {

    /**
     * 根据社团用户标识获得社团注册审核记录
     *
     * @param clubId 社团用户标识
     * @return 社团注册审核记录
     */
    ClubAudit selectByClubId(int clubId);

    /**
     * 根据审核标题模糊搜索获得社团注册审核记录，分页获取
     *
     * @param auditTitle 社团名称
     * @param offset   跳过读数
     * @param limit    读取数
     * @return 社团注册审核记录集
     */
    List<ClubAudit> queryByClubName(String auditTitle, int offset, int limit);

    /**
     * 获得所有审核
     *
     * @param offset 跳过读数
     * @param limit  读取数
     * @return 社团注册审核记录集
     */
    List<ClubAudit> loadAllClubAudit(int offset, int limit);

    /**
     * 查看所有未审核的社团审核消息
     * @param offset 跳过读数
     * @param limit 读取数
     * @return 社团注册审核记录集
     */
    List<ClubAudit> loadAllUnAudit(int offset,int limit);

    /**
     * 计算还没审核的消息数
     * @return 未审核数
     */
    int countUnAudit();

    /**
     * 发送社团审核给校社联
     * @param clubAudit 社团审核内容
     * @return Operation.SUCCESSFULLY; 发送成功 Operation.FAILED 发送失败
     */
    int sendClubAudit(ClubAudit clubAudit);

    /**
     * 根据审核id删除某个社团的审核
     * @param id  审核id
     * @return Operation.SUCCESSFULLY; 删除成功 Operation.FAILED 删除失败
     */
    int deleteClubAuditById(int id);

    /**
     * 通过审核id和社团审核类更新审核状态信息
     * @param id  审核id
     * @param clubAudit
     * @return Operation.SUCCESSFULLY; 删除成功 Operation.FAILED 删除失败
     */
    int updateClubAuditById(int id,ClubAudit clubAudit);

    /**
     * 通过审核id和响应获取社团审核的文件
     * @param id 审核id
     * @param response 审核id
     * @return Operation.SUCCESSFULLY; 删除成功 Operation.FAILED 删除失败
     */
    int getClubAuditFileById(int id, HttpServletResponse response);

    /**
     * 通过审核id和响应在线预览社团审核文件
     * @param id 审核id
     * @param response 响应
     * @return Operation.SUCCESSFULLY; 删除成功 Operation.FAILED 删除失败
     */
    int viewClubAuditFileById(int id,HttpServletResponse response);

    /**
     * 通过审核id和审核信息类对审核消息进行通过或拒绝
     * @param id 审核id
     * @param resultMsg 审核结果信息
     * @return Operation.SUCCESSFULLY; 删除成功 Operation.FAILED 删除失败
     */
    Integer passOrRejectClubAuditByIdAndResultMsg(int id, ClubAuditResultMsg resultMsg);
}
