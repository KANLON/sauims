package com.fekpal.dao;

import com.fekpal.domain.User;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by APone on 2017/8/15.
 * userDao接口
 */
@Repository
public interface UserDao {

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
     * 添加成员
     *
     * @param user User
     */
    void addUser(User user);

    /**
     * 修改用户部分信息
     *
     * @param user User
     */
    void updateUser(User user);

    /**
     * 判断是否存在相同用户名称
     *
     * @param userName String
     * @return boolean
     */
    boolean hadAccount(String userName);

    /**
     * 判断是否存在相同的邮箱
     *
     * @param email String
     * @return boolean
     */
    boolean hadEmail(String email);
}
