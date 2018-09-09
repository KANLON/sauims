package com.fekpal.service.impl;

import com.fekpal.api.MemberOrgService;
import com.fekpal.common.base.BaseServiceImpl;
import com.fekpal.common.base.CRUDException;
import com.fekpal.common.base.ExampleWrapper;
import com.fekpal.common.constant.AvailableState;
import com.fekpal.common.constant.ClubRole;
import com.fekpal.common.constant.MemberState;
import com.fekpal.common.constant.Operation;
import com.fekpal.common.session.SessionLocal;
import com.fekpal.common.utils.TimeUtil;
import com.fekpal.dao.mapper.MemberOrgMapper;
import com.fekpal.dao.mapper.PersonMapper;
import com.fekpal.dao.model.MemberOrg;
import com.fekpal.dao.model.Person;
import com.fekpal.web.controller.sau.SauCenterController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.util.List;

/**
 * Created by APone on 2018/2/28 3:01.
 */
@Service
public class MemberOrgServiceImpl extends BaseServiceImpl<MemberOrgMapper, MemberOrg> implements MemberOrgService {

    private static Logger logger =     LogManager.getLogger(MemberOrgServiceImpl.class);

    @Autowired
    private HttpSession session;

    @Autowired
    private PersonMapper personMapper;

    @Override
    public MemberOrg selectByOrgId(int id) {
        int uid = SessionLocal.local(session).getUserIdentity().getUid();
        ExampleWrapper<MemberOrg> example = new ExampleWrapper<>();
        example.eq("org_id", id)
                .and().eq("person_id", uid)
                .and().eq("available", AvailableState.AVAILABLE);
        return mapper.selectFirstByExample(example);
    }

    @Override
    public MemberOrg selectById(int id) {
        int uid = SessionLocal.local(session).getUserIdentity().getUid();
        ExampleWrapper<MemberOrg> example = new ExampleWrapper<>();
        example.eq("id", id)
                .and().eq("person_id", uid)
                .and().eq("available", AvailableState.AVAILABLE);
        return mapper.selectFirstByExample(example);
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = {Exception.class})
    public int joinOrganizationByOrgId(int id) {
        int uid = SessionLocal.local(session).getUserIdentity().getUid();
        Person person = personMapper.selectByPrimaryKey(uid);
        if (!isValidInfo(person)) return Operation.INPUT_INCORRECT;
        //获取该用户是否已经加入了该社团，如果已经加入了或已经申请了,则返回失败
        ExampleWrapper<MemberOrg> example = new ExampleWrapper<>();
        example.eq("member.person_id",person.getPersonId())
                .and().eq("member.org_id",id)
                .and().eq("member_state",MemberState.STILL_BEING);
        if(mapper.countByExample(example)>=1){
            return Operation.FAILED;
        }

        MemberOrg memberOrg = new MemberOrg();
        memberOrg.setPersonId(uid);
        memberOrg.setOrgId(id);
        memberOrg.setMemberDuty(ClubRole.MEMBER);
        memberOrg.setJoinTime(new Timestamp(TimeUtil.currentTime()));
        memberOrg.setMemberState(MemberState.STILL_BEING);
        memberOrg.setAvailable(AvailableState.AUDITING);
        int row = mapper.insert(memberOrg);
        if (row != 1) throw new CRUDException("加入组织操作异常：" + row);
        return Operation.SUCCESSFULLY;
    }

    /**
     * 检查加入组织的用户是否完成必要信息的填写
     *
     * @param person 普通用户
     * @return 是否符合
     */
    private boolean isValidInfo(Person person) {
        return (!StringUtils.isEmpty(person.getRealName()) && !StringUtils.isEmpty(person.getDepartment()) &&
                !StringUtils.isEmpty(person.getMajor()) && !StringUtils.isEmpty(person.getStudentId()) &&
                person.getEnrollmentYear() > 0);
    }

    @Override
    public List<MemberOrg> loadAllOrg(int offset, int limit) {
        int uid = SessionLocal.local(session).getUserIdentity().getUid();
        ExampleWrapper<MemberOrg> example = new ExampleWrapper<>();
        example.eq("person_id", uid).and().eq("available", AvailableState.AVAILABLE);
        return mapper.selectByExample(example, offset, limit);
    }

    @Override
    public Integer countAllOrg() {
        int uid = SessionLocal.local(session).getUserIdentity().getUid();
        ExampleWrapper<MemberOrg> example = new ExampleWrapper<>();
        example.eq("person_id", uid).and().eq("available", AvailableState.AVAILABLE);
        return mapper.countByExample(example);
    }
}
