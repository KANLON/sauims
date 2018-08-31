package com.fekpal.service.impl;

import com.fekpal.api.OrgMemberAuditService;
import com.fekpal.common.base.BaseServiceImpl;
import com.fekpal.dao.mapper.OrgMemberMapper;
import com.fekpal.dao.model.OrgMember;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by APone on 2018/2/26 19:20.
 */
@Service
public class OrgMemberAuditServiceImpl extends BaseServiceImpl<OrgMemberMapper, OrgMember> implements OrgMemberAuditService {

    @Autowired
    private HttpSession session;

    @Override
    public OrgMember selectAuditById(int id) {
        return null;
    }

    @Override
    public List<OrgMember> selectNewAudit(int offset, int limit) {
        return null;
    }

    @Override
    public int passMemberApply(int id) {
        return 0;
    }

    @Override
    public int rejectMemberApply(int id) {
        return 0;
    }

    @Override
    public List<OrgMember> loadAllAudit(int offset, int limit) {
        return null;
    }
}
