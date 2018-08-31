package com.fekpal.web.controller;

import com.fekpal.api.RegisterService;
import com.fekpal.common.constant.Operation;
import com.fekpal.common.constant.ResponseCode;
import com.fekpal.common.json.JsonResult;
import com.fekpal.common.utils.IPUtil;
import com.fekpal.common.utils.TimeUtil;
import com.fekpal.dao.model.UniqueMsg;
import com.fekpal.service.model.domain.ClubReg;
import com.fekpal.service.model.domain.PersonReg;
import com.fekpal.service.model.domain.SauReg;
import com.fekpal.web.model.CaptchaMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;

/**
 * 注册的控制类
 * Created by hasee on 2017/8/17.
 */
@Controller
public class RegisterController {

    @Autowired
    private RegisterService registerService;

    /**
     * 发送普通用户注册验证码
     *
     * @param msg 验证来源封装
     * @return json数据封装
     */
    @ResponseBody
    @RequestMapping(value = "/reg/person/captcha", method = RequestMethod.POST)
    public JsonResult<String> sendPersonCaptcha(@RequestBody CaptchaMsg msg) {
        int state = registerService.sendPersonEmailCaptcha(msg.getEmail());
        JsonResult<String> result = new JsonResult<>();
        if (state == Operation.SUCCESSFULLY) {
            result.setStateCode(ResponseCode.RESPONSE_SUCCESS, "验证码发送成功");
        } else if (state == Operation.FAILED) {
            result.setStateCode(ResponseCode.RESPONSE_ERROR, "此邮箱已被注册");
        }
        return result;
    }

    /**
     * 发送社团用户注册验证码
     *
     * @param msg 验证来源封装
     * @return json数据封装
     */
    @ResponseBody
    @RequestMapping(value = "/reg/club/captcha", method = RequestMethod.POST)
    public JsonResult<String> sendClubCaptcha(@RequestBody CaptchaMsg msg) {
        int state = registerService.sendClubEmailCaptcha(msg.getEmail());
        JsonResult<String> result = new JsonResult<>();
        if (state == Operation.SUCCESSFULLY) {
            result.setStateCode(ResponseCode.RESPONSE_SUCCESS, "验证码发送成功");
        } else if (state == Operation.FAILED) {
            result.setStateCode(ResponseCode.RESPONSE_ERROR, "此邮箱已被注册");
        }
        return result;
    }

    /**
     * 社团注册的方法
     *
     * @param reg 社团用户注册信息封装
     * @return 标准json数据
     */
    @ResponseBody
    @RequestMapping(value = "/reg/club", method = RequestMethod.POST)
    public JsonResult<String> clubRegister(@ModelAttribute ClubReg reg, HttpServletRequest request) {
        long time = TimeUtil.currentTime();
        Timestamp timestamp = new Timestamp(time);
        String ip = IPUtil.getUserIP(request);
        reg.setCurrentTime(time);
        reg.setLoginIp(ip);
        reg.setLoginTime(timestamp);
        reg.setRegisterIp(ip);
        reg.setRegisterTime(timestamp);

        int state = registerService.insertClubReg(reg);
        JsonResult<String> result = new JsonResult<>();
        setRegHintMsg(state, result);
        return result;
    }

    /**
     * 校社联注册的方法,目前测试用
     *
     * @param reg 校社联用户注册信息封装
     * @return 标准json数据
     */
    @ResponseBody
    @RequestMapping(value = "/reg/sau", method = RequestMethod.POST)
    public JsonResult<String> sauRegister(@RequestBody SauReg reg, HttpServletRequest request) {
        long time = TimeUtil.currentTime();
        Timestamp timestamp = new Timestamp(time);
        String ip = IPUtil.getUserIP(request);
        reg.setLoginIp(ip);
        reg.setLoginTime(timestamp);
        reg.setRegisterIp(ip);
        reg.setRegisterTime(timestamp);

        int state = registerService.insertSauReg(reg);
        JsonResult<String> result = new JsonResult<>();
        setRegHintMsg(state, result);
        return result;
    }

    /**
     * 个人用户注册的方法
     *
     * @param reg 个人登录信息
     * @return 标准json数据
     */
    @ResponseBody
    @RequestMapping(value = "/reg/person", method = RequestMethod.POST)
    public JsonResult<String> personRegister(@RequestBody PersonReg reg, HttpServletRequest request) {
        long time = TimeUtil.currentTime();
        Timestamp timestamp = new Timestamp(time);
        String ip = IPUtil.getUserIP(request);
        reg.setCurrentTime(time);
        reg.setLoginIp(ip);
        reg.setLoginTime(timestamp);
        reg.setRegisterIp(ip);
        reg.setRegisterTime(timestamp);

        int state = registerService.insertPersonReg(reg);
        JsonResult<String> result = new JsonResult<>();
        setRegHintMsg(state, result);
        return result;
    }

    /**
     * 检测是否存相同用户名
     *
     * @param msg 唯一信息封装
     * @return 标准json
     */
    @ResponseBody
    @RequestMapping(value = "/reg/check", method = RequestMethod.POST)
    public JsonResult<UniqueMsg> checkExit(@RequestBody UniqueMsg msg) {
        UniqueMsg data = registerService.checkExitInfo(msg);
        JsonResult<UniqueMsg> result = new JsonResult<>();
        result.setStateCode(ResponseCode.RESPONSE_SUCCESS, "检测成功");
        result.setData(data);
        return result;
    }

    /**
     * 操作结果
     *
     * @param state  操作状态
     * @param result json封装
     */
    private static void setRegHintMsg(int state, JsonResult<String> result) {
        if (state == Operation.SUCCESSFULLY) {
            result.setStateCode(ResponseCode.RESPONSE_SUCCESS, "注册成功");
        } else if (state == Operation.FAILED) {
            result.setStateCode(ResponseCode.RESPONSE_ERROR, "注册失败");
        } else if (state == Operation.CAPTCHA_INCORRECT) {
            result.setStateCode(ResponseCode.RESPONSE_ERROR, "验证码错误");
        }
    }
}
