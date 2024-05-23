//package com.team2.resumeeditorproject.gpt.config;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.CorsRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//
//@Configuration
//public class ChatConfig implements WebMvcConfigurer {
//    @Override
//    public void addCorsMappings(CorsRegistry corsRegistry) {
//        corsRegistry.addMapping("/**")
//                .allowedOrigins("http://localhost:3000", "https://resume-editor-frontend-indol.vercel.app")
//                .allowedMethods("*")
//                .allowedHeaders("*")
//                .allowCredentials(true)
//                .maxAge(3600); // 예를 들어, preflight 결과를 1시간 동안 캐시
//    }
//}
