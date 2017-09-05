package com.fekpal.dao;

import com.fekpal.domain.ClubMember;
import com.fekpal.domain.Person;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by APone on 2017/8/23.
 * ClubMemberDao接口
 */
@Repository
public interface ClubMemberDao {


    /**
     * 根据普通用户id和社团id获得社员信息
     *
     * @param personId int
     * @param clubId   int
     * @return d
     */
    ClubMember getMemberByPersonAndCLub(int personId, int clubId);

    /**
     * 通过普通用户id获得所在所有参加社团
     *
     * @param personId int
     * @return List
     */
    List<ClubMember> getClubMemberByPersonId(int personId);

    /**
     * 通过社团id获得所有社员
     *
     * @param cluId int
     * @return List
     */
    List<Person> getAllMemberByClubId(int cluId);

    /**
     * 根据id获得社员信息
     *
     * @param id int
     * @return CLubMember
     */
    ClubMember getMemberById(int id);

    /**
     * 添加新的社员
     *
     * @param clubMember d
     */
    void addClubMember(ClubMember clubMember);

    /**
     * 更新社员信息
     *
     * @param clubMember d
     */
    void updateClubMember(ClubMember clubMember);

    /**
     * 获得所有所在社团社员的审核信息
     *
     * @param clubId int
     * @return List
     */
    List<ClubMember> getAllAuditingByClubId(int clubId);
}
