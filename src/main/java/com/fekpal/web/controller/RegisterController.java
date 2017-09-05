package com.fekpal.web.controller;

import com.fekpal.cons.ResponseCode;
import com.fekpal.domain.controllerDomain.ClubRegisterMsg;
import com.fekpal.domain.controllerDomain.PersonRegisterMsg;
import com.fekpal.tool.*;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import sun.security.provider.MD5;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;

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
    @ResponseBody
    @RequestMapping(value = "/reg/code", method = RequestMethod.GET)
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
                    + captcha + "<br/><br/>" + "验证码十分钟内有效");
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
     * @param file       上传的文件
     * @param clubMsgMap 社团信息
     * @param request    请求
     * @param session    session会话
     * @return 标准json数据
     */
    @ResponseBody
    @RequestMapping(value = "/reg/club", method = RequestMethod.POST)
    public Map<String, Object> clubRegister(@RequestParam(required = true) MultipartFile[] file, @RequestParam(required = false) Map<String, Object> clubMsgMap, HttpServletRequest request, HttpSession session) {
        BaseReturnData returnData = new BaseReturnData();
        //判断用户是否登录
        if (session.getAttribute("userCode") != null) {
            returnData.setStateCode(1, "你已经登录的了，请退出后在注册。");
            return returnData.getMap();
        }

        //从前端传传过来的社团信息中获取相应内容
        String userName = clubMsgMap.get("userName").toString().trim();
        String realName = clubMsgMap.get("realName").toString().trim();
        String passwordMD5 = MD5Tool.md5((String) clubMsgMap.get("password")).toString().trim();
        String captcha =  clubMsgMap.get("captcha").toString().trim();
        String phone = clubMsgMap.get("phone").toString().trim();
        String clubName =  clubMsgMap.get("clubName").toString().trim();
        String clubType =  clubMsgMap.get("clubType").toString().trim();
        String description = clubMsgMap.get("description").toString().trim();
        String email =  clubMsgMap.get("email").toString().trim();

        //验证输入的值是否为空
        if(StringUtils.isEmpty(userName) || StringUtils.isEmpty(realName) || StringUtils.isEmpty(realName)
                || StringUtils.isEmpty(realName) || StringUtils.isEmpty(realName) || StringUtils.isEmpty(realName)
                || StringUtils.isEmpty(realName)){
            returnData.setStateCode(ResponseCode.REQUEST_ERROR,"输入了空的值");
            return returnData.getMap();
        }

        //如果上传的文件不符合符合条件
        if ((Integer) (handleFile(file).get("code")) != 0) {
            //如果上传的文件不符合条件，返回相应内容
            return handleFile(file);
        }

        //如果session域中验证码非空
        if (session.getAttribute("emailCaptcha") != null) {
            //从session域中得到验证码和邮箱
            String sessionCaptcha = (String) session.getAttribute("emailCaptcha");
            String sessionEmail = (String) session.getAttribute("email");
            out.println("session验证是：" + sessionCaptcha);
            out.println("register验证码是：" + captcha);
            //校验验证码和邮箱是否相等和时间是否过了10分钟
            if (sessionCaptcha.trim().toLowerCase().equals(captcha.trim().toLowerCase())
                    && sessionEmail.trim().toLowerCase().equals(email.trim().toLowerCase())
                    && TimeTool.cmpTime((String) session.getAttribute("time"))) {
                //清除session
                session.invalidate();

                //调用工具类检验用户名，密码等是否符合规范
                // TODO: 2017/8/18

                //在service层验证描述，提交的文件的安全性等
                // TODO: 2017/9/2

                //检验用户名是否重复
                // TODO: 2017/8/18

                //调用service层检验将社团信息存入数据库
                //将文件存入服务器中的与本项目同目录的//MySAUImages/clubRegister文件夹中，返回文件名
                List<String> fileNameList = FileUploadTool.fileHandle(file, request, "clubRegister");

                out.println("存入数据库社团注册信息为：" + clubMsgMap + "。注册的文件名是：" + fileNameList.get(0));
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
    @ResponseBody
    @RequestMapping("/reg/person")
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
                    && TimeTool.cmpTime((String) session.getAttribute("time"))) {
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

    /**
     * 处理上传文件的方法
     *
     * @param myfiles 上传的文件
     * @return 上传信息是否正确
     */
    public Map<String, Object> handleFile(MultipartFile[] myfiles) {
        BaseReturnData returnData = new BaseReturnData();
        //判断文件格式和大小是否符合
        for (MultipartFile myfile : myfiles) {
            if (!myfile.isEmpty()) {
                if (myfile.getSize() > 1024 * 1024 * 10) {
                    returnData.setStateCode(ResponseCode.REQUEST_ERROR, "文件大于10m请重新上传");
                    return returnData.getMap();
                }
                if (!myfile.getContentType().toString().equals("application/vnd.openxmlformats-officedocument.wordprocessingml.document")
                        && !myfile.getContentType().toString().equals("application/msword")) {
                    returnData.setStateCode(ResponseCode.REQUEST_ERROR, "上传的文件不符合格式，请重新上传");
                    return returnData.getMap();
                }
            } else {
                returnData.setStateCode(1, "没上传文件，请重新上传");
                return returnData.getMap();
            }
        }
        //如果上传的文件为空，返回提示语句
        if (myfiles == null) {
            returnData.setStateCode(1, "没上传文件，请重新上传");
        }
        return returnData.getMap();
    }
}
