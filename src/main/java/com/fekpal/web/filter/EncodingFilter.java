package com.fekpal.web.filter;

import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * 自定义编码过滤器，解决乱码问题，主要处理get请求乱码问题
 * @author  zhangcanlong
 * @2018/4/11
 */
public class EncodingFilter extends OncePerRequestFilter {

    private String encoding;

    private boolean forceEncoding = false;

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    public void setForceEncoding(boolean forceEncoding) {
        this.forceEncoding = forceEncoding;
    }

    public String filter(HttpServletRequest request, String input) {
        String ret = input;
        // 客户端请求参数值可能为(null)服务端过滤掉当null处理即可
        if (input == null || input.trim().equals("(null)")) {
            return null;
        }
        final String method = request.getMethod();
        // 该处可以实现各种业务的自定义的过滤机制
        if ("get".equalsIgnoreCase(method)) {
            try {
                ret = new String(input.getBytes("UTF-8"), this.encoding);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return ret;
    }

    @Override
    protected void doFilterInternal(final HttpServletRequest request,
                                    HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //设置request和response的编码格式
        if (this.encoding != null && (this.forceEncoding || request.getCharacterEncoding() == null)) {
            request.setCharacterEncoding(this.encoding);
            if (this.forceEncoding) {
                response.setCharacterEncoding(this.encoding);
            }
        }
            //对request中的参数进行编码格式的转换
            filterChain.doFilter(new HttpServletRequestWrapper(request) {
                @Override
                public String getParameter(String name) {
                    String value = super.getParameter(name);
                    return filter(this, value);
                }

                @Override
                public String[] getParameterValues(String name) {
                    String[] values = super.getParameterValues(name);
                    if (values == null) {
                        return null;
                    }
                    for (int i = 0; i < values.length; i++) {
                        values[i] = filter(this, values[i]);
                    }
                    return values;
                }
            }, response);

    }
}

