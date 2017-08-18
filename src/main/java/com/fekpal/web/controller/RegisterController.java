package com.fekpal.web.controller;

import com.fekpal.domain.controllerDomain.ClubRegisterMsg;
import com.fekpal.domain.controllerDomain.PersonRegisterMsg;
import com.fekpal.tool.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.mail.MessagingException;
import javax.servlet.http.HttpSession;
import java.util.Map;

import static java.lang.System.out;

/**
 * 注册的控制类
 * Created by hasee on 2017/8/17.
 */
@Controller
public class RegisterController {


    //spring的依赖注入发送邮件对象
    @Autowired
    private MailHtmlTool mailHtmlTool;



    /**
     * 发送邮箱验证码
     *
     * @param session session
     * @param email   发送过来的邮箱
     * @return 返回标准json数据
     */
    @RequestMapping("/reg/code")
    @ResponseBody
    public Map<String, Object> sendEmailCaptcha(HttpSession session, @RequestParam(value = "email") String email) {
        BaseReturnData returnData = new BaseReturnData();

        //多次点击发送验证码
        // TODO: 2017/8/18
        
        //链接数据库判断邮箱是否已经存在
        // TODO: 2017/8/18

        //将邮件地址去空格
        email = email.trim();
        //判断用户是否登录
        if (session.getAttribute("userCode") != null) {
            returnData.setStateCode(1, "你已经登录的了，请退出后在注册。");
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
            mailHtmlTool.sendHtml(email, "校社联管理系统发给您的验证码", "您的邮箱验证码是：<br/>"
                    + captcha + "<br/><br/>"+"验证码十分钟内有效");
            out.println("已经发送了验证码：" + captcha);
        } catch (MessagingException e) {
            returnData.setStateCode(1, "邮件发送失败，请点击重新发送。如果多次点击发送后，依然不成功，请稍后再试。");
        } finally {
            return returnData.getMap();
        }
    }

    /**
     * 社团注册的方法
     *
     * @param clubRegisterMsg 社团的信息
     * @param session         session会话
     * @return 标准json数据
     */
    @RequestMapping("/reg/club")
    @ResponseBody
    public Map<String, Object> clubRegister(@RequestBody ClubRegisterMsg clubRegisterMsg, HttpSession session) {
        BaseReturnData returnData = new BaseReturnData();
        //判断用户是否登录
        if (session.getAttribute("userCode") != null) {
            returnData.setStateCode(1, "你已经登录的了，请退出后在注册。");
            return returnData.getMap();
        }

        //如果session域中验证码非空
        if (session.getAttribute("emailCaptcha") != null) {
            out.println("得到了验证码");
            //从session域中得到验证码和邮箱
            String sessionCaptcha = (String) session.getAttribute("emailCaptcha");
            String sessionEmail = (String) session.getAttribute("email");
            out.println("session验证是：" + sessionCaptcha);
            out.println("register验证码是：" + clubRegisterMsg.getCaptcha());
            //校验验证码和邮箱是否相等和时间是否过了10分钟
            if (sessionCaptcha.trim().toLowerCase().equals(clubRegisterMsg.getCaptcha().trim().toLowerCase())
                    && sessionEmail.trim().toLowerCase().equals(clubRegisterMsg.getEmail().trim().toLowerCase())
                    && TimeTool.cmpTime((String) session.getAttribute("time")) ) {
                //清除session
                session.invalidate();

                //调用工具类检验用户名，密码等是否符合规范
                // TODO: 2017/8/18  

                //检验用户名是否重复
                // TODO: 2017/8/18

                //调用service层检验将社团信息存入数据库

                out.println("存入数据库社团注册信息为：" + clubRegisterMsg);

            } else {
                returnData.setStateCode(1, "验证码不正确，请重新输入！");
            }
        } else {
            returnData.setStateCode(1, "还没有发送验证码，请重新点击发送。");
        }
        return returnData.getMap();
    }

    /**
     * 个人用户登录的方法
     *
     * @param personRegisterMsg 个人登录信息
     * @param session           session会话
     * @return 标准json数据
     */
    @RequestMapping("/reg/person")
    @ResponseBody
    public Map<String, Object> personRegister(@RequestBody PersonRegisterMsg personRegisterMsg, HttpSession session) {
        BaseReturnData returnData = new BaseReturnData();

        //判断是否登录
        if (session.getAttribute("userCode") != null) {
            returnData.setStateCode(1, "你已经登录的了，请退出后在注册。");
            return returnData.getMap();
        }

        //如果session域中验证码非空
        if (session.getAttribute("emailCaptcha") != null) {
            out.println("得到了验证码");
            //从session域中得到验证码和邮箱
            String sessionCaptcha = (String) session.getAttribute("emailCaptcha");
            String sessionEmail = (String) session.getAttribute("email");

            out.println("session验证是：" + sessionCaptcha);
            out.println("register验证码是：" + personRegisterMsg.getCaptcha());

            //校验验证码,邮箱和时间是否过了10分钟是否相等
            if (sessionCaptcha.trim().toLowerCase().equals(personRegisterMsg.getCaptcha().trim().toLowerCase())
                    && sessionEmail.trim().toLowerCase().equals(personRegisterMsg.getUserName().trim().toLowerCase())
                    && TimeTool.cmpTime((String) session.getAttribute("time")) ) {
                //清除session
                session.invalidate();

                //调用工具类检验用户名，密码等是否符合规范
                // TODO: 2017/8/18

                //检验用户名是否重复
                // TODO: 2017/8/18

                //调用service层检验将社团信息存入数据库
                out.println("存入数据库个人注册信息为：" + personRegisterMsg);

            } else {
                returnData.setStateCode(1, "验证码不正确，请重新输入！");
            }
        } else {
            returnData.setStateCode(1, "还没有发送验证码，请重新点击发送。");
        }
        return returnData.getMap();
    }

}
