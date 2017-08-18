package com.fekpal.web.controller;

import com.fekpal.tool.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.servlet.http.HttpSession;
import java.util.Map;

import static java.lang.System.out;

/**
 * 忘记密码功能的控制类
 * Created by hasee on 2017/8/18.
 */
@Controller
public class ForgetPasswordController {

    //spring的依赖注入发送邮件对象
    @Autowired
    private MailHtmlTool mailHtmlTool;

    /**
     * 发送重置密码的邮箱验证码
     * @param email 邮箱地址
     * @param session 用户session会话
     * @return 标准json数据
     */
    @ResponseBody
    @RequestMapping(value = "/security/resetpwd/code",method = RequestMethod.GET)
    public Map<String,Object> sendEmailCaptcha(@RequestParam String email, HttpSession session){
        BaseReturnData returnData = new BaseReturnData();
        //多次点击发送验证码
        // TODO: 2017/8/18

        //链接数据库判断邮箱是否存在，如果不存在，直接返回提示“邮箱不存在，请检查邮箱拼写是否正确”
        // TODO: 2017/8/18

        //将邮件地址去空格
        email = email.trim();
        //判断用户是否登录
        if (session.getAttribute("userCode") != null) {
            returnData.setStateCode(1, "你已经登录了，如要修改密码，请到个人中心操作。");
            return returnData.getMap();
        }

        //检查邮箱是否真实有效是否正确
        if (!EmailValidTool.valid(email, "jootmir.org")) {
            //如果不正确
            returnData.setStateCode(1, "不是有效邮箱，请重新输入");
            return returnData.getMap();
        }

        //从工具类中得到邮箱验证码
        String captcha = ValidateCodeTool.getCaptcha();
        //把验证码，邮箱，时间发入到session中去
        session.setAttribute("emailCaptcha", captcha);
        session.setAttribute("email", email);
        session.setAttribute("time", TimeTool.getTime());

        try {
            mailHtmlTool.sendHtml(email, "校社联管理系统发给您重置密码的验证码", "您重置密码的验证码是：<br/>"
                    + captcha + "<br/><br/>"+"验证码十分钟内有效");
            out.println("已经发送了验证码：" + captcha);
        } catch (MessagingException e) {
            returnData.setStateCode(1, "邮件发送失败，请点击重新发送。如果多次点击发送后，依然不成功，请稍后再试。");
        } finally {
            return returnData.getMap();
        }
    }

    /**
     * 重置密码的方法
     * @param newPassword 新密码
     * @param captcha 验证码
     * @param session session
     * @return 标准json数据
     */
    @ResponseBody
    @RequestMapping(value = "/security/resetpwd")
    public Map<String,Object> resetPassword(@RequestParam(value = "newPassword") String newPassword,@RequestParam(value = "captcha") String captcha, HttpSession session){
        out.println("发送过来的数据为：newPassword="+newPassword+"    captcha="+captcha );
        BaseReturnData returnData = new BaseReturnData();

        //调用工具查看验证码和密码是否符合规则
        // TODO: 2017/8/18

        //检查验证码和密码是否为空
        if(StringUtils.isEmpty(newPassword) || StringUtils.isEmpty(captcha)) {
            returnData.setStateCode(1,"验证码或者密码为空，请重新输入");
            return returnData.getMap();
        }else{
            //将验证码空格去掉
            captcha = captcha.trim();
        }
        if(session.getAttribute("emailCaptcha")!=null) {
            //比较验证码是否正确和时间是否在有效期内
            if (session.getAttribute("emailCaptcha").toString().trim().toLowerCase().equals(captcha.toLowerCase())
                    && TimeTool.cmpTime(TimeTool.getTime())) {
                //清除session
                session.invalidate();

                if (true) {
                    //将新密码存入数据库
                    out.println("存入数据库" + newPassword);
                } else {
                    returnData.setStateCode(1, "密码格式错误，请重新输入");
                }
            } else {
                returnData.setStateCode(1, "验证码错误，请重新输入");
            }
        }else{
            returnData.setStateCode(1,"还没发送验证码，请点击发送");
        }
        return returnData.getMap();
    }


}

