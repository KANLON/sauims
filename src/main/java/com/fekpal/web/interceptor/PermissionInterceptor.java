package com.fekpal.web.interceptor;

import com.fekpal.common.session.SessionLocal;
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
 *
 * @author zhangcanlong
 */

public class PermissionInterceptor implements HandlerInterceptor {

    private static Logger logger = LogManager.getLogger(PermissionInterceptor.class);

    private static Map<Integer, String> permissionMap = new HashMap<Integer, String>();

    //将权限的url存入到session中去
    static {
        permissionMap.put(0, "/member");
        permissionMap.put(1, "/club");
        permissionMap.put(2, "/sau");
    }

    //在执行handler之前来执行的
    //用于用户认证校验、用户权限校验
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String url = request.getRequestURI();
        //判断是不是公开的url
        if (CommonErrorHandle.commonUrlCheck(url)) {
            return true;
        }
        //从配置文件获取公共访问地址
        List<String> common_urls = new ArrayList<>();
        common_urls.add("/msg");
        common_urls.add("/security/email");
        common_urls.add("/security/pwd");
        common_urls.add("/logout");
        for (String common_url : common_urls) {
            if (url.startsWith(common_url)) {
                return true;
            }
        }
        //权限访问地址
        HttpSession session = request.getSession(false);
        int permissionId = SessionLocal.local(session).getUserIdentity().getAuth();
        String permissionUrl = permissionMap.get(permissionId);
        if (url.startsWith(permissionUrl)) {
            return true;
        }
        //执行到这里拦截，跳转到无权访问的提示页面
        logger.info("跳转到无权访问的提示页面");
        return CommonErrorHandle.errorHandle(request,response,"无权访问！");
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
            ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }
}
