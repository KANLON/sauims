package com.fekpal.api;

import com.fekpal.common.base.BaseService;
import com.fekpal.dao.model.User;
import com.fekpal.service.model.domain.SecureMsg;

/**
 * Created by APone on 2018/2/7 0:49.
 * 账号安全服务接口
 * 该接口提供账号用户安全修改操作
 */
public interface AccountSecureService {

    /**
     * 重置身份记录密码,适用于未登录的用户
     *
     * @param record 信息封装
     *               传入参数：新密码newPassword, 验证码code, 当前时间currentTime
     * @return 是否修改成功
     * 参考参数：Operation.SUCCESSFULLY 成功 Operation.FAILED 失败 Operation.CAPTCHA_INCORRECT 验证码错误
     */
    int resetPwd(SecureMsg record);

    /**
     * （申请）忘记身份记录密码，适用于未登录用户，根据填写邮箱（查伪)发送验证码
     *
     * @param record 信息封装
     *               传入参数：邮箱email
     * @return 是否可以拥有合法修改
     * 参考参数：Operation.SUCCESSFULLY 成功 Operation.FAILED 失败
     */
    int forgetPwdByEmail(SecureMsg record);

    /**
     * 更新（修改）密码记录，适用于登录用户
     *
     * @param record 信息封装
     *               传入参数：新密码newPassword，旧密码oldPassword
     * @return 是否更新成功
     * 参考参数：Operation.SUCCESSFULLY 成功 Operation.FAILED 失败
     */
    int modifyPwd(SecureMsg record);

    /**
     * 确认（申请）修改邮箱地址，在真正修改邮箱地址前的认证
     *
     * @return 是否发送确认
     * 参考参数：Operation.SUCCESSFULLY 成功 Operation.FAILED 失败
     */
    int sendModifyEmailCaptcha();

    /**
     * 确认（申请）修改邮箱地址，在真正修改邮箱地址前的认证
     * @param record 信息封装
     *               传入参数：新邮箱email
     * @return
     * 参考参数：Operation.SUCCESSFULLY 成功 Operation.FAILED 失败
     */
    int sendModifyEmailCaptcha(SecureMsg record);


    /**
     * 更新（修改）邮箱地址记录，适用于登录用户
     *
     * @param record 信息封装
     *               传入参数：新邮箱newEmail， 验证码code， 当前时间currentTime
     * @return 是否修改成功
     * 参考参数：Operation.SUCCESSFULLY 成功 Operation.FAILED 失败 Operation.CAPTCHA_INCORRECT 验证码错误
     */
    int modifyEmail(SecureMsg record);
}
