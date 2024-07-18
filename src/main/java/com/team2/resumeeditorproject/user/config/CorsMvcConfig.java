package com.team2.resumeeditorproject.user.config;

import java.util.List;

import com.team2.resumeeditorproject.statistics.interceptor.TrafficInterceptor;
import com.team2.resumeeditorproject.user.resolver.UserArgumentResolver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
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

    @Autowired
    private UserArgumentResolver userArgumentResolver;

    @Override
    public void addCorsMappings(CorsRegistry corsRegistry) {
        corsRegistry.addMapping("/**")
                .allowedOrigins("https://reditor.me","https://www.reditor.me", "http://localhost:3000")
                .allowedMethods("*")
                .allowedHeaders("*")
                .allowCredentials(true)
                .maxAge(3600);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(trafficInterceptor);
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(userArgumentResolver);
    }
}
