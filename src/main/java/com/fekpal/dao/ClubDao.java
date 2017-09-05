package com.fekpal.dao;

import com.fekpal.domain.Club;
import com.fekpal.domain.User;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by APone on 2017/8/15.
 * ClubDao的接口
 */
@Repository
public interface ClubDao {

    /**
     * 根据社团id获得社团
     *
     * @param clubId int
     * @return Club
     */
    Club getClubByClubId(int clubId);

    /**
     * 根据名称获得社团
     *
     * @param clubName String
     * @return Club
     */
    Club getClubByClubName(String clubName);

    /**
     * 根据社团用户id获得社团
     *
     * @param userId int
     * @return Club
     */
    Club getClubByUserId(int userId);

    /**
     * 根据社团名称模糊搜索
     *
     * @param clubName String
     * @return CLub
     */
    List<Club> findClubByClubName(String clubName, int start, int count);

    /**
     * 是否有相同的社团名称
     *
     * @param clubName String
     * @return boolean
     */
    boolean hadClubName(String clubName);

    /**
     * 添加社团
     *
     * @param club Club
     */
    void addClub(Club club);

    /**
     * 更新社团信息
     *
     * @param club Club
     */
    void updateClub(Club club);


    /**
     * 喜爱人数加一
     *
     * @param clubId int
     */
    void updateLikeNumber(int clubId);

    /**
     * 获得所有的社团
     *
     * @param start int
     * @param count int
     * @return List
     */
    List<Club> loadAllClub(int start, int count);
}
