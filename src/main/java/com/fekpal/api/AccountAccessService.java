package com.fekpal.api;

import com.fekpal.common.base.BaseService;
import com.fekpal.dao.model.User;
import com.fekpal.service.model.domain.SecureMsg;
import com.fekpal.service.model.domain.LoginResult;

import java.io.OutputStream;

/**
 * Created by APone on 2018/2/19 21:13.
 * 账号认证接口
 * 该接口提供账号的登录，登出，验证码的操作
 */
public interface AccountAccessService {

    /**
     * 用户身份信息进行用户登录
     *
     * @param record 信息封装
     *               传入参数：用户名userName,密码password, 验证码code, 当前时间currentTime
     * @return 登录结果
     * 参考参数：登录状态loginState：Operation.SUCCESSFULLY 成功
     * Operation.FAILED 失败
     * Operation.CAPTCHA_INCORRECT 验证码错误
     * 用户权限authority：系统的角色
     */
    LoginResult login(SecureMsg record);

    /**
     * 生成登录验证码，图片形式
     *
     * @param out 输出流
     */
    void sendLoginCaptchaImage(OutputStream out);

    /**
     * 用户是否仍然处于登录状态
     *
     * @return 是否处于登录状态
     */
    boolean isLogin();

    /**
     * 用户登出系统
     *
     * @return 是否登出成功
     */
    boolean logout();
}
