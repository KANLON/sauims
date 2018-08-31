package com.fekpal.api;

import com.fekpal.common.base.BaseService;
import com.fekpal.dao.model.Person;
import com.fekpal.service.model.domain.PersonMsg;
import com.fekpal.web.model.PersonDetail;

import java.io.OutputStream;
import java.util.List;

/**
 * Created by APone on 2017/9/5.
 * 普通用户信息接口
 * 该接口主要提功普通用户信息增删查改操作
 */
public interface PersonService extends BaseService<Person> {

    /**
     * 根据普通用户标识修改用户头像
     *
     * @param msg 普通用户修改信息封装
     *            传入参数：头像文件logo
     * @return 头像名
     */
    String updateLogo(PersonMsg msg);

    /**
     * 更新普通用户信息
     *
     * @param msg 普通用户修改信息封装
     *            传入参数：
     * @return 更新状态 Operation.SUCCESSFULLY 成功 Operation.FAILED 失败 Operation.INPUT_INCORRECT 存在相同的昵称
     */
    int updatePersonInfo(PersonMsg msg);

    /**
     * 根据昵称获得个人
     *
     * @param name 昵称
     * @return 普通用户记录
     */
    Person selectByNickname(String name);

    /**
     * 根据用户身份标识获得个人
     *
     * @param id 用户身份标识
     * @return 普通用户记录
     */
    Person selectByUserId(int id);

    /**
     * 根据普通用户标识获得个人
     *
     * @return 普通用户记录
     */
    Person selectByPrimaryId();

    /**
     * 根据昵称模糊搜索普通用户信息记录，分页读取
     *
     * @param name   昵称
     * @param offset 跳过读数
     * @param limit  读取数
     * @return 普通用户信息记录集
     */
    List<Person> queryByNickname(String name, int offset, int limit);

    /**
     * 是否有相同的昵称
     *
     * @param name 昵称
     * @return 是否存在
     */
    boolean isExitNickname(String name);

    /**
     * 获得所有普通用户信息记录集
     *
     * @param offset 跳过读数
     * @param limit  读取数
     * @return 普通用户记录集
     */
    List<Person> loadAllPerson(int offset, int limit);


    /**
     * 得到个人中心的的详细信息
     * @return 个人中心的详细信息类
     */
    PersonDetail selectPersonDetailByPrimaryId();

    /**
     * 得到个人头像
     * @param output 输出流
     * @return 是否成功
     */
    int getPersonLogo(OutputStream output);
}
