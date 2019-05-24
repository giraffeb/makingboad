package com.example.board.security.config;

import javax.servlet.FilterConfig;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Enumeration;


public class XssFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest)request;
        MyServletRequest wrapperRequest = new MyServletRequest(httpRequest);

        Enumeration penum = httpRequest.getParameterNames();

        while(penum.hasMoreElements()){
            String key = (String)penum.nextElement();
            String param = wrapperRequest.getParameter(key);

            String newParam = param.replace("&", "&amp;")
                    .replace("<", "&lt;")
                    .replace(">", "&gt;");

            wrapperRequest.setParameter(key, newParam);
        }


        chain.doFilter(wrapperRequest, response);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }
}
