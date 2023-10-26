package com.example.dbcafe.member.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.concurrent.TimeUnit;

@Configuration
public class MenuConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(final ResourceHandlerRegistry registry) {
        //url에서 resource로 접속하면
        registry.addResourceHandler("/resource/**")
                //addResourceLocations의 저장한 서버의 경로를 불러옴
                .addResourceLocations("file:///home/tomcat/server/resource/")
                // 접근 파일 캐싱 시간
                .setCacheControl(CacheControl.maxAge(1, TimeUnit.MINUTES));;

    }
}
