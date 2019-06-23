package com.example.board.security.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean xssFilter(){
        FilterRegistrationBean registrationBean = new FilterRegistrationBean(new XssFilter());
        registrationBean.addUrlPatterns("/writePost", "/updatePost");
        return registrationBean;
    }
}
