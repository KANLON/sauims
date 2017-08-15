package com.fekpal.web.controller;

import com.fekpal.domain.User;
import com.fekpal.tool.BaseReturnData;

import com.fekpal.tool.ValidateCodeUtils;
import com.sun.deploy.net.HttpResponse;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

import static java.lang.System.out;

/**
 * 登陆相关的方法
 * Created by hasee on 2017/8/14.
 */
@Controller
@RequestMapping("/login")
public class loginController {

    /**
     * 生成登陆验证码，并向浏览器输出
     * @param response
     * @param session
     */
    @RequestMapping(value = "/code",method = RequestMethod.GET)
    public void captchaCode (HttpServletResponse response, HttpSession session){
        try {
            //生成一张随机验证码图片，并写出到浏览器
            OutputStream out = response.getOutputStream();
            String sCode = ValidateCodeUtils.genNewCode(out);
            //把sCode共享给用户登录时使用
            session.setAttribute("sCode", sCode);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
