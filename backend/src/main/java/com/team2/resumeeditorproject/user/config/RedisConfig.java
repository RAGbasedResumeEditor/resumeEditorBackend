package com.team2.resumeeditorproject.user.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

// Redis 사용을 위한 기본 Configuration
@Configuration
public class RedisConfig {
    @Value("${spring.redis.host}")
    private String host;
    @Value("${spring.redis.port}")
    private int port;

    @Bean
    public RedisConnectionFactory redisConnectionFactory(){ // application.yml에 입력한 host, port를 redis와 연결해준다.
        return new LettuceConnectionFactory(host, port);
    }//redisConnectionFactory()

    @Bean
    public RedisTemplate<?,?> redisTemplate(){ // RedisTemplate에 LettuceConnectionFactory를 적용해준다.
        RedisTemplate<?,?> redisTemplate=new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory());
        return redisTemplate;
    }

}
