package com.fekpal.api;

import com.fekpal.common.base.BaseService;
import com.fekpal.dao.model.OrgMember;

import java.util.List;

/**
 * Created by APone on 2018/2/26 19:17.
 * 成员申请加入组织接口
 * 该接口提供普通用户加入相应组织的申请操作，组织审核成员有效信息的查询，修改操作
 */
public interface OrgMemberAuditService extends BaseService<OrgMember> {

    /**
     * 根据组织成员关系标识获得该成员（所属此组织）组织成员记录
     *
     * @param id 组织成员关系标识
     * @return 组织成员记录
     */
    OrgMember selectAuditById(int id);

    /**
     * 获取该组织新的成员加入申请
     *
     * @param offset 跳过读数
     * @param limit  读取数
     * @return 组织成员记录集
     */
    List<OrgMember> selectNewAudit(int offset, int limit);

    /**
     * 通过该成员加入组织申请，审核结果将以信息通知
     *
     * @param id 组织成员关系标识
     * @return 审核状态 Operation.SUCCESSFULLY 成功 Operation.FAILED 失败
     */
    int passMemberApply(int id);

    /**
     * 否决该用户加入组织申请，审核结果将以信息通知
     *
     * @param id 组织成员关系标识
     * @return 审核状态 Operation.SUCCESSFULLY 成功 Operation.FAILED 失败
     */
    int rejectMemberApply(int id);


    /**
     * 加载所有历史申请加入该组织的（不包含未审核，包含否决以及通过）的组织成员记录集，安分页获取
     *
     * @param offset 跳过读数
     * @param limit  读取数
     * @return 组织成员记录
     */
    List<OrgMember> loadAllAudit(int offset, int limit);
}
