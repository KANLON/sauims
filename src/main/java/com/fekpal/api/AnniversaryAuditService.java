package com.fekpal.api;

import com.fekpal.common.base.BaseService;
import com.fekpal.dao.model.AnniversaryAudit;
import com.fekpal.web.model.ClubSubmitAnnMsg;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author zhangcanlong
 * @date 2018/4/6
 * 社团年度审核接口
 * 该接口提供添加，审核，删除，查看社团年度审核的操作
 */
public interface AnniversaryAuditService extends BaseService<AnniversaryAudit> {

    /**
     * 发送年度审核消息给校社联
     * @param submitAudit 提交的年度审核消息内容
     * @return Operation.SUCCESSFULLY; 删除成功 Operation.FAILED 删除失败
     */
    int sendAuditMessage(ClubSubmitAnnMsg submitAudit);


    /**
     * 加载全部审核内容，除了删除的
     * @param offset 开始数
     * @param limit 查询条数
     * @return 审核消息的类的list集合
     */
    List<AnniversaryAudit> loadAllAudit(int offset,int limit);

    /**
     * 统计全部审核内容，除了删除的
     * @return 审核消息记录数
     */
    Integer countAllAudit();

    /**
     * 加载全部未审核的社团年度审核消息
     * @param offset 开始数
     * @param limit 查询条数
     * @return 审核消息的类的list集合
     */
    List<AnniversaryAudit> loadAllUnAudit(int offset,int limit);

    /**
     * 加载全部已经审核的年度通过审核消息
     * @param offset 开始数
     * @param limit 查询条数
     * @return 审核消息的类的list集合
     */
    List<AnniversaryAudit> loadAllHaveAudit(int offset,int limit);

    /**
     * 通过审核标题查找年度审核消息
     * @param auditTitle 审核标题
     * @param offset 开始数
     * @param limit 查询条数
     * @return 审核消息的类的list集合
     */
    List<AnniversaryAudit> queryByAuditTitle(String auditTitle,int offset,int limit);


    /**
     * 通过统计审核标题查找年度审核消息
     * @param auditTitle 审核标题
     * @return 审核消息记录数
     */
    Integer countByAuditTitle(String auditTitle);

    /**
     * 通过社团id查询某个社团的全部审核消息
     * @param orgId 社团id
     * @param offset 开始数
     * @param limit 查询条数
     * @return 审核消息的类的list集合
     */
    List<AnniversaryAudit> selectByOrgId(int orgId,int offset,int limit);

    /**
     * 通过年度审核id，查询某个年度审核
     * @param auditId 年度审核id
     * @return 年度审核类
     */
    AnniversaryAudit selectByAuditId(int auditId);

    /**
     * 未审核的年度审核数量
     * @return 未审核年度审核消息数
     */
    int countUnAuditNum();

    /**
     * 通过审核消息id删除某条审核消息
     * @param auditId 审核消息id
     * @return Operation.SUCCESSFULLY; 删除成功 Operation.FAILED 删除失败
     */
    int deleteById(int auditId);

    /**
     * 得到年度审核的附件
     * @param auditId 年度审核id
     * @param response 响应
     * @return Operation.SUCCESSFULLY; 删除成功 Operation.FAILED 删除失败
     */
    int getAuditFileById(int auditId, HttpServletResponse response);

    /**
     * 在线预览年度审核文件
     * @param auditId 审核id
     * @param response 响应 返回html文件流
     * @return Operation.SUCCESSFULLY; 删除成功 Operation.FAILED 删除失败
     */
    int viewAuditFileById(int auditId,HttpServletResponse response);

    /**
     * 通过年度审核类来更新年度审核信息
     * @param anniversaryAudit 年度审核类
     * @return Operation.SUCCESSFULLY; 删除成功 Operation.FAILED 删除失败
     */
    int updateAuditStateByAnnModel(AnniversaryAudit anniversaryAudit);


}
