package com.kanboo.www.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                // allowedOrigins => 각자 로컬 호스트 포트번호 바꿔서 사용
                // 첫번째 매개변수 : 자바, 두번째 매개변수 : 프론트
                .allowedOrigins("http://localhost:8099", "http://localhost:8080")
                .exposedHeaders("jtw-token");
    }
}