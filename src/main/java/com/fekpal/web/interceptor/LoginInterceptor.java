package com.fekpal.web.interceptor;

import com.fekpal.common.session.SessionLocal;
import com.fekpal.web.controller.ReceiveMsgController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * 用户身份拦截器
 * Created by hasee on 2018/3/6.
 * @author zhangcanlong
 */

public class LoginInterceptor implements HandlerInterceptor {

    static Logger logger =   LogManager.getLogger(LoginInterceptor.class);

    //用于执行handle之前来执行，用于用户认证检验/用户授权检验
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String url = request.getRequestURI();
       //获取到公开的地址
        if(url.equalsIgnoreCase("/")){ return true;}
        List<String> open_urls = new ArrayList<>();
        open_urls.add("/index.html");
        open_urls.add("/login");
        open_urls.add("/images");
        open_urls.add("/component");
        open_urls.add("/javascripts");
        open_urls.add("/stylesheets");
		open_urls.add("/security/resetpwd");
        open_urls.add("/reg");
        open_urls.add("/index");
        open_urls.add("/resource");
        //临时开放链接，用来测试
        open_urls.add("/favicon.ico");
        open_urls.add("/sign");
        open_urls.add("/clubNews");
        //如果是公开地址，则放行
        for(String open_url:open_urls){
            if(url.startsWith(open_url)){
                return true;
            }
        }
        //判断用户是否已经登录
        HttpSession session  = request.getSession(false);
        if(session == null ){
            logger.info("session为空,用户没有登录，准备跳转");
            response.sendRedirect("/index.html");
            return false;
        }else{
            SessionLocal local=SessionLocal.local(session);
            if(local.isExitUserIdentity()){
                logger.info("用户已经登录");
                return true;
            }
        }
      //执行到这里跳转到登录页面，用户进行身份认证
        response.sendRedirect("/index.html"); 
        return false;
    }

    //在执行handler返回modelAndView之前来执行
    //如果需要向页面提供一些公用 的数据或配置一些视图信息，使用此方法实现 从modelAndView入手
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }
    //执行handler之后执行此方法
    //作系统 统一异常处理，进行方法执行性能监控，在preHandle中设置一个时间点，在afterCompletion设置一个时间，两个时间点的差就是执行时长
    //实现 系统 统一日志记录
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }
}
