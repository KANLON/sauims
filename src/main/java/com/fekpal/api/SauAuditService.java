package com.fekpal.api;

import com.fekpal.common.exception.BusinessException;
import com.fekpal.dao.model.SauAuditRegister;

import java.util.List;

/**
 * 校社联注册审核社团或校社联成员类，service层接口
 * @author zhangcanlong
 * @date 2018/7/10
 */
public interface SauAuditService {

    /**
     * 校社联查询所有审核信息
     * @param offset 页码
     * @param limit 每页条数
     * @return 校社联注册审核类目录
     * @throws BusinessException
     */
    List<SauAuditRegister> getAllAudit(Integer offset,Integer limit) throws BusinessException;

    /**
     * 校社联查询所有审核信息
     * @return 校社联注册审核类目录
     * @throws BusinessException
     */
    Integer countAllAudit() throws BusinessException;

    /**
     * 根据审核状态查找审核信息
     * @param auditState 审核状态，0表示审核未通过，1表示审核通过，2表示待审核
     * @param offset 页码
     * @param limit 每页条数
     * @return 校社联注册审核类目录
     * @throws BusinessException
     */
    List<SauAuditRegister> getAuditByState(Integer auditState,Integer offset, Integer limit) throws BusinessException;

    /**
     * 根据审核状态查找审核信息
     * @param auditState 审核状态，0表示审核未通过，1表示审核通过，2表示待审核
     * @return 校社联注册审核类目录
     * @throws BusinessException
     */
    Integer countAuditByState(Integer auditState) throws BusinessException;


    /**
     * 根据查找内容（审核标题）查找审核信息
     * @param findContent  查找的内容
     * @param offset 页码
     * @param limit 每页条数
     * @return 校社联注册审核类目录
     * @throws BusinessException
     */
    List<SauAuditRegister> getAuditByFindContent(String findContent,Integer offset, Integer limit) throws BusinessException;

    /**
     * 根据查找内容（审核标题）查找审核信息
     * @param findContent  查找的内容
     * @return 校社联注册审核类目录
     * @throws BusinessException
     */
    Integer countAuditByFindContent(String findContent) throws BusinessException;

    /**
     * 根据审核状态和查找内容，查询审核信息
     * @param auditState 审核状态，0表示审核未通过，1表示审核通过，2表示待审核
     * @param findContent 要查找的内容
     * @param offset 页码
     * @param limit 每页条数
     * @return 校社联注册审核类目录
     * @throws BusinessException
     */
    List<SauAuditRegister> getAuditByStateAndFindContent(Integer auditState, String findContent,Integer offset,Integer limit) throws BusinessException;

    /**
     * 根据审核状态和查找内容，查询审核信息
     * @param auditState 审核状态，0表示审核未通过，1表示审核通过，2表示待审核
     * @param findContent 要查找的内容
     * @return 校社联注册审核类目录
     * @throws BusinessException
     */
    Integer countAuditByStateAndFindContent(Integer auditState, String findContent) throws BusinessException;

    /**
     * 根据审核状态，审核内容和审核角色，查询审核信息，如果其中一个为空
     * @param auditState 审核状态
     * @param findContent 审核内容
     * @param role 审核角色
     * @param offset 页码
     * @param limit 每页条数
     * @return 校社联注册审核类目录
     * @throws BusinessException
     */
    List<SauAuditRegister> getAuditByByStateAndFindContentAndRole(Integer auditState,String findContent,Integer role,Integer offset,Integer limit) throws BusinessException;

    /**
     * 根据审核状态，审核内容和审核角色，查询审核信息，如果其中一个为空
     * @param auditState 审核状态
     * @param findContent 审核内容
     * @param role 审核角色
     * @return 校社联注册审核类目录
     * @throws BusinessException
     */
    Integer countAuditByByStateAndFindContentAndRole(Integer auditState,String findContent,Integer role) throws BusinessException;

}
