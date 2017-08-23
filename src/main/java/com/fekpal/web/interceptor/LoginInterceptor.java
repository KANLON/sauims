package com.fekpal.web.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static java.lang.System.out;

/**
 * 登陆验证的拦截器
 * Created by hasee on 2017/8/20.
 */
public class LoginInterceptor implements HandlerInterceptor {
    /**
     *  Handler执行之前调用这个方法
     */
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
       out.println("执行了拦截器的方法");
        //获取请求的URL
        String url = request.getRequestURI();

        //url:/login/go || indexpage.html || /login/code 是公开的，其他的url是都进行拦截
        if(url.indexOf("/login/go")>=0 || url.indexOf("indexPage.html")>=0 || url.indexOf("/login/code")>=0){
            return true;
        }
        //获取session
        HttpSession session = request.getSession();
        int userId = 0 ;
        if(session.getAttribute("userCode")==null){
            //不符合条件的，跳转到登录界面
//            response.sendRedirect("/SAU/indexPage.html");
            request.getRequestDispatcher("/SAU/indexPage.html").forward(request,response);
            return false;
        }else{
            userId = (Integer) session.getAttribute("userCode");
            return true;
        }
    }

    /**
     *Handler执行之后调用这个方法，返回之前调用这个方法
     */
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    /**
     * Handler执行之后调用这个方法
     */
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
