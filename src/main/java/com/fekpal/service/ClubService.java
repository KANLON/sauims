package com.fekpal.service;

import com.fekpal.domain.Club;

import java.util.List;

/**
 * Created by APone on 2017/9/5.
 */
public interface ClubService {

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
    boolean checkSameClubName(String clubName);


    /**
     * 更新社团信息
     *
     * @param club Club
     */
    void updateClubInfo(Club club);


    /**
     * 喜爱人数加一
     *
     * @param clubId int
     */
    void likeNumberPlus(int clubId);

    /**
     * 获得所有的社团
     *
     * @param start int
     * @param count int
     * @return List
     */
    List<Club> loadAllClub(int start, int count);
}
