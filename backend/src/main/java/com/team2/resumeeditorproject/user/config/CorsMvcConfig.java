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
        corsRegistry.addMapping("/**") // 모든 컨트롤러 경로에 대해서
                .allowedOrigins("http:localhost:3000").allowedHeaders("*"); //3000번 주소에서 오는 요청 허용
    }
}
