package com.lzb.www.controller.filter;


import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.lzb.www.constant.WebConstant.CONTENT_TYPE;
import static com.lzb.www.constant.WebConstant.UTF_8;


/**
 * 统一处理中文乱码
 *
 * @author lzb
 */
@WebFilter("/*")
public class StringFormatHandlingFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        request.setCharacterEncoding(UTF_8);
        response.setContentType(CONTENT_TYPE);

        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }
}
