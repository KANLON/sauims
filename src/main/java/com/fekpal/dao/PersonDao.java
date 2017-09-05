package com.fekpal.dao;

import com.fekpal.domain.Club;
import com.fekpal.domain.Person;
import com.fekpal.domain.User;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by APone on 2017/8/15.
 * PersonDao的接口,个人普通用户信息，校社联用户和社团以此类推
 */
@Repository
public interface PersonDao {

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
    boolean hadNickName(String nickName);

    /**
     * 添加个人
     *
     * @param person Person
     */
    void addPerson(Person person);

    /**
     * 更新个人
     *
     * @param person Person
     */
    void updatePerson(Person person);


    /**
     * 获得所有个人
     *
     * @param start int
     * @param count int
     * @return List
     */
    List<Person> loadAllPerson(int start, int count);
}
