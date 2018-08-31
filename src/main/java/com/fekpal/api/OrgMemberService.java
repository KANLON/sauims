package com.fekpal.api;

import com.fekpal.common.base.BaseService;
import com.fekpal.dao.model.OrgMember;
import com.fekpal.web.model.AuditResult;

import java.util.List;

/**
 * Created by APone on 2017/9/5.
 * 组织成员接口
 * 该接口提供组织内的成员的添加，查询，删除等操作
 */
public interface OrgMemberService extends BaseService<OrgMember> {

    /**
     * 根据组织成员（普通用户）标识获取该有效（所属该组织） 组织成员记录
     *
     * @param id 普通用户标识
     * @return 组织成员记录
     */
    OrgMember selectByPersonId(int id);

    /**
     * 根据组织成员关系标识获得该有效（所属该组织） 组织成员记录
     *
     * @param id 组织成员关系记录标识
     * @return 组织成员记录
     */
    OrgMember selectById(int id);

    /**
     * 修改该组织成员在该组织的关系，将其状态改为离开
     *
     * @param id 组织成员关系标识
     * @return 取消状态 Operation.SUCCESSFULLY 成功 Operation.FAILED 失败
     */
    int cancelMemberById(int id);

    /**
     * 解除该组织成员在该组织的关系，关系无效化
     *
     * @param id 组织成员关系标识
     * @return 取消状态 Operation.SUCCESSFULLY 成功 Operation.FAILED 失败
     */
    int deleteMemberById(int id);

    /**
     * 加载所有该组织的所有组织成员，按分页获取
     *
     * @param offset 跳过读数
     * @param limit  读取数
     * @return 组织成员记录集
     */
    List<OrgMember> loadAllMember(int offset, int limit);

    /**
     * 加载所有该组织的所有未审核的组织成员，按页获取
     * @param offset 跳过读数
     * @param limit 读取数
     * @return 组织成员记录集
     */
    List<OrgMember> loadAllUnAuditMember(int offset,int limit);

    /**
     * 加载所有该组织的所有审核的组织成员，按页获取
     * @param offset 跳过读数
     * @param limit 读取数
     * @return 组织成员记录集
     */
    List<OrgMember> loadAllAuditMember(int offset,int limit);

    /**
     * 统计所有该组织的所有审核的组织成员数
     * @return 组织成员记录数
     */
    Integer countAllAuditMember();


    /**
     * 根据真实姓名用模糊搜索个人名姓名
     * @param realName 真实姓名
     * @param offset 跳过读数
     * @param limit 读取数
     * @return 组织成员记录表
     */
    List<OrgMember> queryByRealName(String realName,int offset,int limit);

    /**
     * 根据真实姓名统计组织成员数
     * @param realName 真实姓名
     * @return 组织成员记录数
     */
    Integer countAuditByRealName(String realName);


    /**
     * 社团对个人加入社团进行通过或拒绝
     * @param auditId 审核id
     * @param auditResult 审核结果类
     * @return 操作状态 Operation.SUCCESSFULLY 成功 Operation.FAILED 失败
     */
    int passOrRejectAuditByIdAndModel(int auditId,AuditResult auditResult);
}
