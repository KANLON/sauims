package com.fekpal.web.interceptor;

import com.fekpal.common.session.SessionLocal;
import com.fekpal.web.controller.member.MemberOrgMsgController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户授权拦截器
 * Created by hasee on 2018/3/6.
 * @author zhangcanlong
 */

public class PermissionInterceptor implements HandlerInterceptor {

    private static Logger logger =   LogManager.getLogger(PermissionInterceptor.class);

    private static Map<Integer,String> permissionMap = new HashMap<Integer,String>();
    //将权限的url存入到session中去
    static {
        permissionMap.put(0,"/member");
        permissionMap.put(1,"/club");
        permissionMap.put(2,"/sau");
    }
    //在执行handler之前来执行的
    //用于用户认证校验、用户权限校验
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String url = request.getRequestURI();
        logger.info("url为："+url);
        if(url.equalsIgnoreCase("/")){ return true;}
        //获取到公开的地址
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
        //从配置文件获取公共访问地址
        List<String> common_urls = new ArrayList<>();
        common_urls.add("/msg");
        common_urls.add("/security/email");
        common_urls.add("/security/pwd");
        common_urls.add("/logout");
        for(String common_url : common_urls){
            if(url.startsWith(common_url)){
                return true;
            }
        }
        //权限访问地址
        HttpSession session = request.getSession(false);
        int permissionId = SessionLocal.local(session).getUserIdentity().getAuth();
        String permissionUrl = permissionMap.get(permissionId);
        if(url.startsWith(permissionUrl)){
            return true;
        }
       //执行到这里拦截，跳转到无权访问的提示页面
        logger.info("跳转到无权访问的提示页面");
        response.sendRedirect("/index.html"); 
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }
}
