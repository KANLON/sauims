package com.fekpal.web.controller;

import com.fekpal.api.AccountSecureService;
import com.fekpal.common.constant.Operation;
import com.fekpal.common.constant.ResponseCode;
import com.fekpal.common.json.JsonResult;
import com.fekpal.common.utils.TimeUtil;
import com.fekpal.service.model.domain.SecureMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


/**
 * 安全功能的控制类，包含忘记密码，发送邮箱验证
 * Created by hasee on 2017/8/29.
 */
@Controller
public class SecurityController {

    @Autowired
    private AccountSecureService accountSecureService;

    /**
     * 发送重置密码的邮箱验证码
     *
     * @param msg 安全信息封装
     * @return 标准json数据
     */
    @ResponseBody
    @RequestMapping(value = "/security/resetpwd/captcha", method = RequestMethod.POST)
    public JsonResult<String> sendResetPwdCaptcha(@RequestBody SecureMsg msg) {
        msg.setEmail(msg.getEmail());
        int state = accountSecureService.forgetPwdByEmail(msg);

        JsonResult<String> result = new JsonResult<>();
        if (state == Operation.SUCCESSFULLY) {
            result.setStateCode(ResponseCode.RESPONSE_SUCCESS, "验证码发送成功");
        } else if (state == Operation.FAILED) {
            result.setStateCode(ResponseCode.RESPONSE_ERROR, "验证码发送失败");
        }
        return result;
    }

    /**
     * 重置密码
     *
     * @param msg 安全信息封装
     * @return 标准json数据
     */
    @ResponseBody
    @RequestMapping(value = "/security/resetpwd", method = RequestMethod.PUT)
    public JsonResult<String> resetPassword(@RequestBody SecureMsg msg) {
        msg.setCurrentTime(TimeUtil.currentTime());
        int state = accountSecureService.resetPwd(msg);

        JsonResult<String> result = new JsonResult<>();
        if (state == Operation.CAPTCHA_INCORRECT) {
            result.setStateCode(ResponseCode.RESPONSE_ERROR, "验证码错误");
        } else if (state == Operation.FAILED) {
            result.setStateCode(ResponseCode.RESPONSE_ERROR, "重置密码失败");
        } else if (state == Operation.SUCCESSFULLY) {
            result.setStateCode(ResponseCode.RESPONSE_SUCCESS, "重置密码成功");
        }
        return result;
    }

    /**
     * 发送修改邮箱的邮箱验证码
     *
     * @return 是否成功
     */
    @ResponseBody
    @RequestMapping(value = "/security/email/captcha", method = RequestMethod.POST)
    public JsonResult<String> sendModifyEmailCaptcha(@RequestBody SecureMsg msg) {
        int state = accountSecureService.sendModifyEmailCaptcha(msg);

        JsonResult<String> result = new JsonResult<>();
        if (state == Operation.SUCCESSFULLY) {
            result.setStateCode(ResponseCode.RESPONSE_SUCCESS, "验证码发送成功");
        } else if (state == Operation.FAILED) {
            result.setStateCode(ResponseCode.RESPONSE_ERROR, "验证码发送失败");
        }
        return result;
    }

    /**
     * 修改邮箱
     *
     * @return 返回提示信息
     */
    @ResponseBody
    @RequestMapping(value = "/security/email", method = RequestMethod.PUT)
    public JsonResult<String> modifyEmail(@RequestBody SecureMsg msg) {
        msg.setCurrentTime(TimeUtil.currentTime());
        int state = accountSecureService.modifyEmail(msg);

        JsonResult<String> result = new JsonResult<>();
        if (state == Operation.SUCCESSFULLY) {
            result.setStateCode(ResponseCode.RESPONSE_SUCCESS, "修改邮箱成功");
        } else if (state == Operation.CAPTCHA_INCORRECT) {
            result.setStateCode(ResponseCode.RESPONSE_ERROR, "验证码错误");
        }
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/security/pwd", method = RequestMethod.PUT)
    public JsonResult<String> modifyPwd(@RequestBody SecureMsg msg) {
        System.out.println(msg);
        int state = accountSecureService.modifyPwd(msg);

        JsonResult<String> result = new JsonResult<>();
        if (state == Operation.SUCCESSFULLY) {
            result.setStateCode(ResponseCode.RESPONSE_SUCCESS, "修改密码成功");
        } else if (state == Operation.FAILED) {
            result.setStateCode(ResponseCode.RESPONSE_ERROR, "旧密码错误");
        }
        return result;
    }
}
