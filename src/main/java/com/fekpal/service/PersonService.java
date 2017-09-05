package com.fekpal.service;

import com.fekpal.domain.Person;

import java.util.List;

/**
 * Created by APone on 2017/9/5.
 * PersonService接口
 */
public interface PersonService {

    /**
     * 根据社团id获得个人
     *
     * @param personId int
     * @return Person
     */
    Person getPersonByPersonId(int personId);

    /**
     * 根据昵称获得个人
     *
     * @param nickName String
     * @return Person
     */
    Person getPersonByNickName(String nickName);

    /**
     * 根据用户id获得个人
     *
     * @param userId int
     * @return Person
     */
    Person getPersonByUserId(int userId);

    /**
     * 是否有相同的昵称
     *
     * @param nickName String
     * @return boolean
     */
    boolean checkSameNickName(String nickName);

    /**
     * 更新个人
     *
     * @param person Person
     */
    void updatePerson(Person person);

    /**
     * 添加喜爱社团
     *
     * @param personId int
     * @param clubId   int
     */
    void addLikeClub(int personId, int clubId);

    /**
     * 获得所有喜爱的社团id
     *
     * @param personId int
     * @return List
     */
    List<Integer> loadAllLikeByPersonId(int personId);

    /**
     * 获得所有个人
     *
     * @param start int
     * @param count int
     * @return List
     */
    List<Person> loadAllPerson(int start, int count);
}
