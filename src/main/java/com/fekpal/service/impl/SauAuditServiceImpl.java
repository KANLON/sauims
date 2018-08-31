package com.fekpal.service.impl;

import com.fekpal.api.SauAuditService;
import com.fekpal.common.base.ExampleWrapper;
import com.fekpal.common.exception.BusinessException;
import com.fekpal.dao.mapper.SauAuditMapper;
import com.fekpal.dao.model.SauAuditRegister;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 校社联注册审核社团或校社联成员得到实现类，service层接口
 * @author zhangcanlong
 * @date 2018/7/10
 */
@Service
public class SauAuditServiceImpl implements SauAuditService {

    @Autowired
    private SauAuditMapper sauAuditMapper;


    @Override
    public List<SauAuditRegister> getAllAudit(Integer offset, Integer limit) throws BusinessException {
        ExampleWrapper<SauAuditRegister> example = new ExampleWrapper<>();
        example.orderBy("REGISTERTIME",false);
        return sauAuditMapper.selectByExample(example,offset,limit);
    }

    @Override
    public Integer countAllAudit() throws BusinessException {
        return sauAuditMapper.countByExample(new ExampleWrapper<>());
    }

    @Override
    public List<SauAuditRegister> getAuditByState(Integer auditState, Integer offset, Integer limit) throws BusinessException {
        ExampleWrapper<SauAuditRegister> example = new ExampleWrapper<>();
        example.eq("AUDITSTATE",auditState);
        return sauAuditMapper.selectByExample(example,offset,limit);
    }

    @Override
    public Integer countAuditByState(Integer auditState) throws BusinessException {
        ExampleWrapper<SauAuditRegister> example = new ExampleWrapper<>();
        example.eq("AUDITSTATE",auditState);
        return sauAuditMapper.countByExample(example);
    }

    @Override
    public List<SauAuditRegister> getAuditByFindContent(String findContent, Integer offset, Integer limit) throws BusinessException {
        ExampleWrapper<SauAuditRegister> example = new ExampleWrapper<>();
        example.like("AUDITTITLE",findContent);
        return sauAuditMapper.selectByExample(example,offset,limit);
    }

    @Override
    public Integer countAuditByFindContent(String findContent) throws BusinessException {
        ExampleWrapper<SauAuditRegister> example = new ExampleWrapper<>();
        example.like("AUDITTITLE",findContent);
        Integer auditNum = sauAuditMapper.countByExample(example);
        return auditNum;
    }

    @Override
    public List<SauAuditRegister> getAuditByStateAndFindContent(Integer auditState, String findContent, Integer offset, Integer limit) throws BusinessException {
        ExampleWrapper<SauAuditRegister> example = new ExampleWrapper<>();
        example.eq("AUDITSTATE",auditState).and().like("AUDITTITLE",findContent);
        return sauAuditMapper.selectByExample(example,offset,limit);
    }

    @Override
    public Integer countAuditByStateAndFindContent(Integer auditState, String findContent) throws BusinessException {
        ExampleWrapper<SauAuditRegister> example = new ExampleWrapper<>();
        example.eq("AUDITSTATE",auditState).and().like("AUDITTITLE",findContent);
        return sauAuditMapper.countByExample(example);
    }

    @Override
    public List<SauAuditRegister> getAuditByByStateAndFindContentAndRole(Integer auditState, String findContent, Integer role, Integer offset, Integer limit) throws BusinessException {
        ExampleWrapper<SauAuditRegister> example = new ExampleWrapper<>();
        example.eq("AUDITSTATE",auditState).and().like("AUDITTITLE",findContent).and().eq("ROLE",role);
        return sauAuditMapper.selectByExample(example,offset,limit);
    }

    @Override
    public Integer countAuditByByStateAndFindContentAndRole(Integer auditState, String findContent, Integer role) throws BusinessException {
        ExampleWrapper<SauAuditRegister> example = new ExampleWrapper<>();
        example.eq("AUDITSTATE",auditState).and().like("AUDITTITLE",findContent).and().eq("ROLE",role);
        return sauAuditMapper.countByExample(example);
    }
}
