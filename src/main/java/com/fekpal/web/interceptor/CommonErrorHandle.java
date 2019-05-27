package com.fekpal.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * 通用的url验证处理方法
 *
 * @author zhangcanlong
 * @since 2019/5/1 11:50
 **/
public class CommonErrorHandle {

    /**
     * 公开的url
     */
    private static List<String> open_urls = new ArrayList<>();

    static {
        open_urls.add("/");
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
        open_urls.add("/system");
    }

    /**
     * 不通过的请求处理
     *
     * @param request  请求
     * @param response 响应
     * @param result   错误信息
     * @return java.lang.Boolean 返回false
     **/
    public static Boolean errorHandle(HttpServletRequest request, HttpServletResponse response, String result) throws IOException {
        boolean isAjax = (request.getHeader("X-Requested-With") != null && "XMLHttpRequest".equals(request.getHeader(
                "X-Requested-With"))) || "application/json".equals(request.getHeader("Content-Type"));

        //同步请求
        if (!isAjax) {
            response.sendRedirect("/index.html");
        } else {
            String errorMsg = "{\"code\": 1," + "\"msg\": \" " + result + "\"}";
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=UTF-8");
            response.setContentType("application/json;charset=utf-8");
            PrintWriter writer = response.getWriter();
            writer.write(errorMsg);
            writer.flush();
            writer.close();
        }
        return false;
    }


    /**
     * 检查是不是公开的url
     *
     * @param url 公用的url
     * @return java.lang.Boolean
     **/
    public static Boolean commonUrlCheck(String url) {
        //如果是公开地址，则放行
        for (String open_url : open_urls) {
            if (url.startsWith(open_url)) {
                return true;
            }
        }
        return false;
    }
}
