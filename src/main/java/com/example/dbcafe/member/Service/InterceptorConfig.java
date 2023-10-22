package com.example.dbcafe.member.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {



    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        LoginInterceptor loginInterceptor = new LoginInterceptor();

        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/test/*")
                .excludePathPatterns("/index")
                .excludePathPatterns("/test")
                .excludePathPatterns("/save")
                .excludePathPatterns("/login");
    }
}