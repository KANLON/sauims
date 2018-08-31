package com.fekpal.api;

import com.fekpal.dao.model.UniqueMsg;
import com.fekpal.service.model.domain.ClubReg;
import com.fekpal.service.model.domain.PersonReg;
import com.fekpal.service.model.domain.SauReg;

/**
 * Created by APone on 2018/2/9 0:31.
 * 注册服务接口
 * 该接口用于提供注册普通用户，社团用户，校社联用户，以及发送注册验证码的操作
 */
public interface RegisterService {

    /**
     * 检测参数是否存在相同，不唯一
     *
     * @param msg 唯一信息封装
     * @return 信息提示
     */
    UniqueMsg checkExitInfo(UniqueMsg msg);

    /**
     * 注册普通用户
     *
     * @param reg 普通注册信息封装
     *            传入参数：用户名userName，密码password，邮箱地址email，验证码captcha，当前时间currentTime，
     *            登录地址loginIp，登录时间loginTime，注册地址registerIp，注册时间registerTime
     * @return 注册状态 Operation.SUCCESSFULLY 成功 Operation.FAILED 失败 Operation.CAPTCHA_INCORRECT 验证码错误
     */
    int insertPersonReg(PersonReg reg);

    /**
     * 注册校社联用户
     *
     * @param reg 校社联注册信息封装
     *            传入参数：用户名userName，密码password，社长adminName，邮箱地址email，手机号码phone，
     *            校社联名称sauName，描述description，验证码captcha，当前时间currentTime，校社联注册审核文件auditFile，
     *            登录地址loginIp，登录时间loginTime，注册地址registerIp，注册时间registerTime
     * @return 注册状态 Operation.SUCCESSFULLY 成功 Operation.FAILED 失败 Operation.CAPTCHA_INCORRECT 验证码错误
     */
    int insertSauReg(SauReg reg);

    /**
     * 注册社团用户
     *
     * @param reg 社团注册信息封装
     *            传入参数：用户名userName，密码password，社长adminName，邮箱地址email，手机号码phone，
     *            社团名称clubName，社团类型clubType，描述description，验证码captcha，当前时间currentTime，
     *            社团注册审核文件auditFile，登录地址loginIp，登录时间loginTime，注册地址registerIp，
     *            注册时间registerTime
     * @return 注册状态 Operation.SUCCESSFULLY 成功 Operation.FAILED 失败 Operation.CAPTCHA_INCORRECT 验证码错误
     */
    int insertClubReg(ClubReg reg);

    /**
     * 邮箱发送普通用户注册验证码
     *
     * @param email 邮箱地址
     * @return 发送状态状态 Operation.SUCCESSFULLY 成功 Operation.FAILED 失败
     */
    int sendClubEmailCaptcha(String email);

    /**
     * 邮箱发送社团用户注册验证码
     *
     * @param email 邮箱地址
     * @return 发送状态状态 Operation.SUCCESSFULLY 成功 Operation.FAILED 失败
     */
    int sendPersonEmailCaptcha(String email);
}
