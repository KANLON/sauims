package com.fekpal.web.filter;

import com.fekpal.common.utils.StringUtil;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * 跨域问题的filter，用于解决跨域问题，用于前端测试或者分布式发布，测试完成后，要删除该filter
 * @author zhangcanlong
 */
public class CorsFilter implements Filter {

    /**
     * 用来存储 CORS 响应头的值，允许访问的客户端域名，例如：http://web.xxx.com，若为 *，则表示从任意域都能访问，即不做任何限制。
     */
    private String allowOrigin;
    /**
     * 用来存储 CORS 响应头的值，允许访问的方法名，多个方法名用逗号分割，例如：GET,POST,PUT,DELETE,OPTIONS。
     */
    private String allowMethods;
    /**
     * 用来存储 CORS 响应头的值，是否允许请求带有验证信息，若要获取客户端域下的 cookie 时，需要将其设置为 true。
     */
    private String allowCredentials;
    /**
     * 用来存储 CORS 响应头的值，允许服务端访问的客户端请求头，多个请求头用逗号分割，例如：Content-Type
     */
    private String allowHeaders;
    /**
     * 允许客户端访问的服务端响应头，多个响应头用逗号分割
     */
    private String exposeHeaders;

    //从web.xml中得到配置的参数
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        allowOrigin = filterConfig.getInitParameter("allowOrigin");
        allowMethods = filterConfig.getInitParameter("allowMethods");
        allowCredentials = filterConfig.getInitParameter("allowCredentials");
        allowHeaders = filterConfig.getInitParameter("allowHeaders");
        exposeHeaders = filterConfig.getInitParameter("exposeHeaders");
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        if (!StringUtil.isEmpty(allowOrigin)) {
            List<String> allowOriginList = Arrays.asList(allowOrigin.split(","));
            if (allowOriginList != null && allowOriginList.size() !=0 ) {
                String currentOrigin = request.getHeader("Origin");
                if (allowOriginList.contains(currentOrigin)) {
                    response.setHeader("Access-Control-Allow-Origin", currentOrigin);
                }
            }
        }
        if (!StringUtil.isEmpty(allowMethods)) {
            response.setHeader("Access-Control-Allow-Methods", allowMethods);
        }
        if (!StringUtil.isEmpty(allowCredentials)) {
            response.setHeader("Access-Control-Allow-Credentials", allowCredentials);
        }
        if (!StringUtil.isEmpty(allowHeaders)) {
            response.setHeader("Access-Control-Allow-Headers", allowHeaders);
        }
        if (!StringUtil.isEmpty(exposeHeaders)) {
            response.setHeader("Access-Control-Expose-Headers", exposeHeaders);
        }
        chain.doFilter(req, res);
    }

    @Override
    public void destroy() {
    }
}