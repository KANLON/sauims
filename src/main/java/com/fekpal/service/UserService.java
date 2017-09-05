package com.fekpal.service;

import com.fekpal.domain.Club;
import com.fekpal.domain.Person;
import com.fekpal.domain.Sau;
import com.fekpal.domain.User;

/**
 * Created by APone on 2017/8/25.
 * UserService接口
 */
public interface UserService {

    /**
     * 根据用户id获得用户
     *
     * @param userId int
     * @return User
     */
    User getUserByUserId(int userId);

    /**
     * 根据用户名称获得用户
     *
     * @param userName String
     * @return User
     */
    User getUserByUserName(String userName);

    /**
     * 根据用户名和密码获得用户
     *
     * @param userName String
     * @param password String
     * @return User
     */
    User getUserByUserNameAndPassword(String userName, String password);

    /**
     * 根据邮箱获得用户
     *
     * @param email String
     * @return User
     */
    User getUserByEmail(String email);

    /**
     * 添加普通成员
     *
     * @param user   User
     * @param person Person
     */
    void addNewPerson(User user, Person person);

    /**
     * 添加社团成员
     *
     * @param user User
     * @param club Club
     */
    void addNewClub(User user, Club club);

    /**
     * 添加校社联成员
     *
     * @param user User
     * @param sau  Sau
     */
    void addNewSau(User user, Sau sau);

    /**
     * 修改用户认证信息
     *
     * @param user User
     */
    void updateUserInfo(User user);

    /**
     * 判断是否存在相同用户名称
     *
     * @param userName String
     * @return boolean
     */
    boolean checkSameAccount(String userName);

    /**
     * 判断是否存在相同的邮箱
     *
     * @param email String
     * @return boolean
     */
    boolean checkSameEmail(String email);

    /**
     * 根据权限id获得头像
     * @param roleId int
     * @return String
     */
    String getLogoByRoleId(int roleId);
}
