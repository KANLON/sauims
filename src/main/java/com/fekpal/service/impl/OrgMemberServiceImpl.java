package com.fekpal.service.impl;

import com.fekpal.api.OrgMemberService;
import com.fekpal.common.base.BaseServiceImpl;
import com.fekpal.common.base.CRUDException;
import com.fekpal.common.base.ExampleWrapper;
import com.fekpal.common.constant.AuditState;
import com.fekpal.common.constant.AvailableState;
import com.fekpal.common.constant.MemberState;
import com.fekpal.common.constant.Operation;
import com.fekpal.common.session.SessionLocal;
import com.fekpal.common.utils.TimeUtil;
import com.fekpal.dao.mapper.OrgMapper;
import com.fekpal.dao.mapper.OrgMemberMapper;
import com.fekpal.dao.mapper.PersonMapper;
import com.fekpal.dao.model.Org;
import com.fekpal.dao.model.OrgMember;
import com.fekpal.dao.model.Person;
import com.fekpal.web.model.AuditResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by APone on 2017/9/17.
 */
@Service
public class OrgMemberServiceImpl extends BaseServiceImpl<OrgMemberMapper, OrgMember> implements OrgMemberService {

    @Autowired
    private HttpSession session;

    @Autowired
    private PersonMapper personMapper;

    @Autowired
    private OrgMapper orgMapper;

    @Override
    public OrgMember selectByPersonId(int id) {
        int uid = SessionLocal.local(session).getUserIdentity().getUid();
        ExampleWrapper<OrgMember> example = new ExampleWrapper<>();
        example.eq("org_id", uid)
                .and().eq("person_id", id)
                .and().eq("available", AvailableState.AVAILABLE);
        return mapper.selectFirstByExample(example);
    }

    @Override
    public OrgMember selectById(int id) {
        int uid = SessionLocal.local(session).getUserIdentity().getUid();
        ExampleWrapper<OrgMember> example = new ExampleWrapper<>();
        example.eq("id", id)
                .and().eq("org_id", uid)
                .and().eq("available", AvailableState.AVAILABLE);
        return mapper.selectFirstByExample(example);
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = {Exception.class})
    public int cancelMemberById(int id) {
        int uid = SessionLocal.local(session).getUserIdentity().getUid();
        ExampleWrapper<OrgMember> example = new ExampleWrapper<>();
        example.eq("id", id)
                .and().eq("org_id", uid)
                .and().eq("member_state", MemberState.STILL_BEING)
                .and().eq("available", AvailableState.AVAILABLE);
        Timestamp time = new Timestamp(TimeUtil.currentTime());
        OrgMember orgMember = new OrgMember();
        orgMember.setLeaveTime(time);
        orgMember.setMemberState(MemberState.LEAVE);
        int row = mapper.updateByExampleSelective(orgMember, example);
        if (row > 1) throw new CRUDException("撤销组织成员关系异常：" + row);
        return row == 0 ? Operation.FAILED : Operation.SUCCESSFULLY;
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = {Exception.class})
    public int deleteMemberById(int id) {
        int uid = SessionLocal.local(session).getUserIdentity().getUid();
        ExampleWrapper<OrgMember> example = new ExampleWrapper<>();
        example.eq("id", id)
                .and().eq("org_id", uid)
                .and().eq("available", AvailableState.AVAILABLE);
        int row = mapper.deleteByExample(example);
        if (row > 1) throw new CRUDException("删除组织成员关系异常：" + row);
        return row == 0 ? Operation.FAILED : Operation.SUCCESSFULLY;
    }

    @Override
    public List<OrgMember> loadAllMember(int offset, int limit) {
        int uid = SessionLocal.local(session).getUserIdentity().getUid();
        ExampleWrapper<OrgMember> example = new ExampleWrapper<>();
        example.eq("org_id", uid)
                .and().eq("member_state", MemberState.STILL_BEING)
                .and().eq("available", AvailableState.AVAILABLE);
        return mapper.selectByExample(example, offset, limit);
    }

    /**
     * 加载所有该组织的所有未审核的组织成员，按页获取
     *
     * @param offset 跳过读数
     * @param limit  读取数
     * @return 组织成员记录集
     */
    @Override
    public List<OrgMember> loadAllUnAuditMember(int offset, int limit) {
        int orgId = SessionLocal.local(session).getUserIdentity().getUid();
        ExampleWrapper<OrgMember> example = new ExampleWrapper<>();
        example.eq("member_state", AvailableState.AUDITING)
                .and().eq("org_id", orgId)
                .orderBy("join_time", false);
        List<OrgMember> orgMemberList = mapper.selectByExample(example, offset, limit);
        return orgMemberList;
    }

    /**
     * 加载所有该组织的所有审核的组织成员，按页获取
     *
     * @param offset 跳过读数
     * @param limit  读取数
     * @return 组织成员记录集
     */
    @Override
    public List<OrgMember> loadAllAuditMember(int offset, int limit) {
        int orgId = SessionLocal.local(session).getUserIdentity().getUid();
        ExampleWrapper<OrgMember> example = new ExampleWrapper<>();
        example.and().eq("org_id", orgId)
                .orderBy("join_time", false);
        return mapper.selectByExample(example, offset, limit);
    }

    @Override
    public Integer countAllAuditMember() {
        int orgId = SessionLocal.local(session).getUserIdentity().getUid();
        ExampleWrapper<OrgMember> example = new ExampleWrapper<>();
        example.and().eq("org_id", orgId)
                .orderBy("join_time", false);
        return mapper.countByExample(example);
    }

    /**
     * 根据真实姓名用模糊搜索个人名姓名和审核状态为未审核的
     *
     * @param realName 真实姓名
     * @param offset   跳过读数
     * @param limit    读取数
     * @return 组织成员记录表
     */
    @Override
    public List<OrgMember> queryByRealName(String realName, int offset, int limit) {
        if (StringUtils.isEmpty(realName)) {
            return loadAllAuditMember(offset, limit);
        }
        int orgId = SessionLocal.local(session).getUserIdentity().getUid();
        ExampleWrapper<Person> personExample = new ExampleWrapper<>();
        personExample.like("real_name", realName);
        List<Person> personList = personMapper.selectByExample(personExample, offset, limit);
        List<OrgMember> orgMemberList = new ArrayList<>();
        if (personList != null) {
            for (Person person : personList) {
                ExampleWrapper<OrgMember> example = new ExampleWrapper<>();
                example.eq("member.person_id", person.getPersonId()).and().eq("org_id", orgId);
                OrgMember orgMember = mapper.selectFirstByExample(example);
                orgMemberList.add(orgMember);
            }
        }
        return orgMemberList;
    }

    @Override
    public Integer countAuditByRealName(String realName) {
        if (StringUtils.isEmpty(realName)) {
            return countAllAuditMember();
        }
        int orgId = SessionLocal.local(session).getUserIdentity().getUid();
        ExampleWrapper<Person> personExample = new ExampleWrapper<>();
        personExample.like("real_name", realName);
        return personMapper.countByExample(personExample);
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = {Exception.class})
    public int passOrRejectAuditByIdAndModel(int auditId, AuditResult auditResult) {
        int orgId = SessionLocal.local(session).getUserIdentity().getUid();
        //如果该id的审核已经是通过，拒绝或者删除了的，则不能操作
        ExampleWrapper<OrgMember> example = new ExampleWrapper<>();
        example.eq("id", auditId).and().ne("member_state", AuditState.AUDITING);
        if (mapper.countByExample(example) >= 1) {
            return Operation.FAILED;
        }
        ;
        OrgMember orgMember = mapper.selectByPrimaryKey(auditId);
        orgMember.setMemberState(auditResult.getAuditState());
        //如果状态是通过的话，在社团表内也增加人数
        if (auditResult.getAuditState() == AuditState.PASS) {
            Org org = orgMapper.selectByPrimaryKey(orgId);
            org.setMembers(org.getMembers() + 1);
            orgMapper.updateByPrimaryKey(org);
        }
        ExampleWrapper<OrgMember> exampleUpdate = new ExampleWrapper<>();
        exampleUpdate.eq("org_id", orgId).and().eq("id", auditId);
        int row = mapper.updateByExample(orgMember, exampleUpdate);
        return row == 1 ? Operation.SUCCESSFULLY : Operation.FAILED;
    }
}
