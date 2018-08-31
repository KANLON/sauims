package com.fekpal.api;

import com.fekpal.common.base.BaseService;
import com.fekpal.dao.model.User;

import java.util.List;

/**
 * Created by APone on 2017/8/25.
 * 用户身份信息接口
 * 该接口主要提功增删查改所有角色的基本身份信息的操作
 */
public interface UserService extends BaseService<User> {

    /**
     * 根据用户名称获得用户身份记录
     *
     * @param userName 用户名
     * @return 用户身份记录
     */
    User selectByUserName(String userName);

    /**
     * 根据用户名和密码获得用户身份记录
     *
     * @param user 用户身份记录 传入参数：用户名userName，密码password
     * @return 用户身份记录
     */
    User selectByUserNameAndPwd(User user);

    /**
     * 根据邮箱地址获得用户身份记录
     *
     * @param email 邮箱地址
     * @return 用户身份记录
     */
    User selectByEmail(String email);

    /**
     * 判断是否存在相同用户名称
     *
     * @param userName 用户名
     * @return 是否存在相同名称
     */
    boolean isExitAccount(String userName);

    /**
     * 判断是否存在相同的邮箱地址
     *
     * @param email 邮箱地址
     * @return 是否存在相同邮箱地址
     */
    boolean isExitEmail(String email);

    /**
     * 判断是否存在相同的手机号码
     *
     * @param phone 手机号码
     * @return 是否存在相同手机号码
     */
    boolean isExitPhone(String phone);

    /**
     * 是否不存在相同必要信息，既用户的关键信息唯一,尚未出现在数据库
     *
     * @param userName 用户名
     * @param email    邮箱
     * @return 是否成立
     */
    boolean isUnique(String userName, String email);

    /**
     * 根据用户标识更新用户最后一次登录信息
     *
     * @param user 用户身份记录
     *             传入参数：用户标识 userId 登录ip loginIp 登录时间 loginTime
     * @return 更新状态
     */
    int updateLastLoginById(User user);

    /**
     * 获取所有用户身份记录集，按分页获取
     *
     * @param offset Integer 跳过读数
     * @param limit  Integer 读取数
     * @return 用户身份记录集
     */
    List<User> loadAllUser(Integer offset, Integer limit);
}
