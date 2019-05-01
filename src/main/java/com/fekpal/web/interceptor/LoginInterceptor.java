package com.fekpal.web.interceptor;

import com.fekpal.common.session.SessionLocal;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 用户身份拦截器
 * Created by hasee on 2018/3/6.
 *
 * @author zhangcanlong
 */

public class LoginInterceptor implements HandlerInterceptor {

    static Logger logger = LogManager.getLogger(LoginInterceptor.class);

    /**
     * 用于执行handle之前来执行，用于用户认证检验/用户授权检验
     *
     * @param request  请求
     * @param response 响应
     * @param handler  逻辑
     * @return boolean
     **/
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String url = request.getRequestURI();
        //判断是不是公共的url
        if (CommonErrorHandle.commonUrlCheck(url)) {
            return true;
        }
        //判断用户是否已经登录
        HttpSession session = request.getSession(false);
        if (session == null) {
            logger.info("session为空,用户没有登录，准备跳转");
            return CommonErrorHandle.errorHandle(request, response, "用户没有登录");
        } else {
            SessionLocal local = SessionLocal.local(session);
            if (local.isExitUserIdentity()) {
                logger.info("用户已经登录");
                return true;
            }
        }
        //执行到这里跳转到登录页面，用户进行身份认证
        return CommonErrorHandle.errorHandle(request, response, "用户没有登录");
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
            ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
            Exception ex) throws Exception {
    }


}
