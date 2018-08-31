package com.fekpal.api;

import com.fekpal.common.base.BaseService;
import com.fekpal.dao.model.MemberOrg;

import java.util.List;

/**
 * Created by APone on 2018/2/28 2:59.
 * 成员组织接口
 * 该接口提供普通用户对所有已加入的有效的组织进行查询，修改等操作
 */
public interface MemberOrgService extends BaseService<MemberOrg> {

    /**
     * 根据组织标识获得改成员在此组织的有效记录
     *
     * @param id 组织标识
     * @return 成员组织记录
     */
    MemberOrg selectByOrgId(int id);

    /**
     * 根据成员组织关系标识获得此用户在组织的有效记录
     *
     * @param id 成员组织关系
     * @return 成员组织记录
     */
    MemberOrg selectById(int id);

    /**
     * 根据组织标识申请加入
     *
     * @param id 组织标识
     * @return 加入状态 Operation.SUCCESSFULLY 成功 Operation.FAILED 失败 Operation.INPUT_INCORRECT 还未完善信息
     */
    int joinOrganizationByOrgId(int id);

    /**
     * 加载所有该用户加入的有效组织，安分页获取
     *
     * @param offset 跳过读数
     * @param limit  读取数
     * @return 成员组织记录集
     */
    List<MemberOrg> loadAllOrg(int offset, int limit);

    /**
     * 统计所有该用户加入的有效组织数
     *
     * @return 成员组织记录数
     */
    Integer countAllOrg();
}
