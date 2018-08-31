package com.fekpal.dao.mapper;

import com.fekpal.common.base.BaseMapper;
import com.fekpal.dao.model.OrgMember;
import org.springframework.stereotype.Repository;

/**
 * 提供组织成员表，准对某个组织的所有成员操作
 * Created by APone on 2017/8/23.
 */
@Repository
public interface OrgMemberMapper extends BaseMapper<OrgMember> {
}
