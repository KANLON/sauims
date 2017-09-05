package com.fekpal.web.controller;

import com.fekpal.cons.SystemRole;
import com.fekpal.domain.User;
import com.fekpal.domain.UserLogin;
import com.fekpal.service.LoginService;
import com.fekpal.service.UserService;
import com.fekpal.tool.BaseReturnData;

import com.fekpal.tool.MD5Tool;
import com.fekpal.tool.ValidateCodeTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.OutputStream;
import java.util.LinkedHashMap;
import java.util.Map;

import static java.lang.System.out;

/**
 * 登陆相关的方法
 * Created by hasee on 2017/8/14.
 */
@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    /**
     *  用户登录提交方法
     * @param session
     * @param userLogin
     * @return 返回用户信息
     * @throws Exception
     */
    @RequestMapping("/login/go")
    @ResponseBody
    public Map<String,Object> login(@RequestBody UserLogin userLogin, HttpSession session, HttpServletRequest request) throws Exception{
       //模拟用户一直处于登录状态
        session.setAttribute("userCode", 123);

        //创建返回数据模板
        BaseReturnData returnData = new BaseReturnData();

        //判断用户是否已经登陆
        if(session.getAttribute("userCode")!=null){
            returnData.setStateCode(1, "你已经登陆了，不能重复登陆");
            //用于前端回显数据
            returnData.setData(userLogin);
            return  returnData.getMap();
        }

         String code = userLogin.getCaptcha();
         String userName = userLogin.getUserName();


        //模拟service层的方法
        if(session!=null) {
            //打印出验证码
            out.println((String) session.getAttribute("code"));
            if(session.getAttribute("code")!=null) {
                //从session域获取系统生成的验证码
                String scode = (String) session.getAttribute("code");

                //判断验证码是否相等
                if (!code.toLowerCase().equals(scode.toLowerCase())) {
                    out.println("验证码不相等");
                    returnData.setStateCode(1, "验证码不正确");
                    //用于前端回显数据
                    returnData.setData(userLogin);
                    return  returnData.getMap();
                }
            }else{
                returnData.setStateCode(1, "验证码已过期，请重新获得验证码");
                //用于前端回显数据
                returnData.setData(userLogin);
                return  returnData.getMap();
            }

        }
        //模拟service成取到用户

        //调用工具类判断用户名和密码的格式
        // TODO: 2017/8/18

        //判断用户名和密码
        User realUser = userService.getUserByUserName(userName);

        //如果得到的用户为空的话，表示找不到该用户
        if(realUser==null){
            returnData.setStateCode(1,"该用户找不到，请重新输入");
            return ((Map<String, Object>) returnData.getMap());
        }

        if(MD5Tool.md5(realUser.getPassword()).equals(MD5Tool.md5(userLogin.getPassword())) ){
            //清除当前session的验证码
            session.removeAttribute("code");

            //获取用户登陆ip
            String ip = getIpAddr(request);

            out.println("登陆成功"+"。 用户IP为："+ip);
            //登陆成功
            //从service中得到用户对象
            // TODO: 2017/8/19
            //把用户信息放到一个map集合中去，然后返回
            Map<String,Object> userMap = new LinkedHashMap<String, Object>();
            String[] roleList={"普通成员","社团成员","校社联成员"};
            userMap.put("role",roleList[realUser.getAuthority()]);
            userMap.put("userId",realUser.getUserId());
            userMap.put("userName",realUser.getUserName());
            userMap.put("userLogo",userService.getLogoByRoleId(realUser.getAuthority()));
            returnData.setData(userMap);

            //如果Service校验通过，将用户身份记录到session
            session.setAttribute("userCode", realUser.getUserId());
        }else{
            returnData.setStateCode(1, "用户名或密码有误，请重新输入！");
            //用于前端回显数据
            returnData.setData(userLogin);
        }
        return returnData.getMap();
    }

    /**
     * 用户的登出方法
     * @param session
     * @return
     */
    @RequestMapping("/logout")
    @ResponseBody
    public Map<String,Object> logout(HttpSession session){
        BaseReturnData returnData = new BaseReturnData();
        //注销session
        session.invalidate();
        return returnData.getMap();
    }

    /**
     * 生成登陆验证码，并向浏览器输出
     * @param response
     * @param session
     */
    @RequestMapping(value = "/login/code",method = RequestMethod.GET)
    public void captchaCode (HttpServletResponse response, HttpSession session){
        try {
            //生成一张随机验证码图片，并写出到浏览器
            OutputStream out = response.getOutputStream();
            String code = ValidateCodeTool.genNewCode(out);
            //把sCode共享给用户登录时使用
            session.setAttribute("code", code);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * 用来获取用户的登陆的IP地址
     * @param request 用户的请求
     * @return 返回IP地址
     */
    public String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

}
