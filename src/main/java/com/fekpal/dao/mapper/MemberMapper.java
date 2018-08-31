package com.fekpal.dao.mapper;

import com.fekpal.common.base.BaseMapper;
import com.fekpal.common.base.ExampleWrapper;
import com.fekpal.dao.model.Member;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by APone on 2018/3/6 21:47.
 */
@Repository
public interface MemberMapper extends BaseMapper<Member> {

    /**
     * 根据社团id统计社团内部男生数量
     *
     * @param OrgId 社团
     * @return 记录集数量
     */
    int countOrgManNum(int OrgId);

    /**
     * 根据社团id统计社团内部女生数量
     *
     * @param OrgId 社团
     * @return 记录集数量
     */
    int countOrgWomanNum(int OrgId);


    /**
     * 根据社团id和年级统计社团内部某个年级学生的数量
     *
     * @param orgId 社团
     * @param grade 年级
     * @return 记录集数量
     */
    int countOrgGradeNum(@Param("orgId")int orgId, @Param("grade")String grade);
}
