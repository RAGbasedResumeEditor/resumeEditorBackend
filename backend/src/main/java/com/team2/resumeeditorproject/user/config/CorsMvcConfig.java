package com.team2.resumeeditorproject.user.config;

import com.team2.resumeeditorproject.admin.interceptor.TrafficInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
/**
 * 나머지 컨트롤러단의 CORS문제를 처리하기 위한 클래스
 *
 * @author : 신아진
 * @fileName : CorsMvcConfig
 * @since : 05/01/24
 */
@Configuration
public class CorsMvcConfig implements WebMvcConfigurer {

    @Autowired
    private TrafficInterceptor trafficInterceptor;

    @Override
    public void addCorsMappings(CorsRegistry corsRegistry) {
        corsRegistry.addMapping("/**")
                .allowedOrigins("http://localhost:3000", "https://reditor.me")
                .allowedMethods("*")
                .allowedHeaders("*")
                .allowCredentials(true)
                .maxAge(3600); // 예를 들어, preflight 결과를 1시간 동안 캐시
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(trafficInterceptor);
    }
}

