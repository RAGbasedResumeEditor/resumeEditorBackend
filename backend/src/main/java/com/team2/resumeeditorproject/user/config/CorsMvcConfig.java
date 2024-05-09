package com.team2.resumeeditorproject.user.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
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
    @Override
    public void addCorsMappings(CorsRegistry corsRegistry) {
        corsRegistry.addMapping("/**")
                .allowedOrigins("http://localhost:3000", "https://resume-editor-frontend-indol.vercel.app")
                .allowedMethods("*")
                .allowedHeaders("*")
                .allowCredentials(true)
                .maxAge(3600); // 예를 들어, preflight 결과를 1시간 동안 캐시
    }
}

