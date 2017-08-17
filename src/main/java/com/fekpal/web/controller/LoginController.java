package com.fekpal.web.controller;

import com.fekpal.domain.User;
import com.fekpal.domain.UserLogin;
import com.fekpal.service.LoginService;
import com.fekpal.tool.BaseReturnData;

import com.fekpal.tool.ValidateCodeUtils;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import static java.lang.System.out;

/**
 * 登陆相关的方法
 * Created by hasee on 2017/8/14.
 */
@Controller
public class LoginController {

    //注入登陆的service层代码
    @Resource
    private  LoginService loginService;

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
        out.println(userLogin.toString());
        //创建返回数据模板
        BaseReturnData returnData = new BaseReturnData();

        //判断用户是否已经登陆
        if(session.getAttribute("usercode")!=null){
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
        //判断用户名和密码
        User realUser = loginService.getUserByName("张三");

        if(realUser.getPassword().equals(userLogin.getPassword())){
            //清除当前session的验证码
            session.removeAttribute("code");

            //获取用户登陆ip
            String ip = getIpAddr(request);

            out.println("登陆成功"+"。 用户IP为："+ip);
            //登陆成功
            //把用户信息放到一个map集合中去，然后返回
            Map<String,Object> userMap = new LinkedHashMap<String, Object>();
            userMap.put("roleName",realUser.getRoleName());
            userMap.put("userId",realUser.getUserId());
            userMap.put("userName",realUser.getuserName());
            userMap.put("userLogo",realUser.getPersonLogo());
            returnData.setData(userMap);

            //如果Service校验通过，将用户身份记录到session
            session.setAttribute("usercode", realUser.getUserId());
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
            String code = ValidateCodeUtils.genNewCode(out);
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
