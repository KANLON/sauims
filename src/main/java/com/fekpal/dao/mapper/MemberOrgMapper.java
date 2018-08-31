package com.fekpal.dao.mapper;

import com.fekpal.common.base.BaseMapper;
import com.fekpal.common.base.ExampleWrapper;
import com.fekpal.dao.model.MemberOrg;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by APone on 2018/2/26 20:39.
 */
@Repository
public interface MemberOrgMapper extends BaseMapper<MemberOrg> {
}
