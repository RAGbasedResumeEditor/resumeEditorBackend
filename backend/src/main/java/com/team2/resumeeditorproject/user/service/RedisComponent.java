package com.team2.resumeeditorproject.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
@RequiredArgsConstructor
public class RedisComponent {
    // Spring Data Redis 라이브러리를 사용하는데, Spring Data Redis에서도 접근방식은 2가지가 있다.
    // 1. Redis Repository 인터페이스를 생성하여 연결하는 방법
    // 2. Redis Template를 이용하는 방법
    private final StringRedisTemplate redisTemplate; //Redis 접근 위한 Spring Redis 템플릿 클래스

    public void setValues(String key, String value) { //지정된 키(key)에 값을 저장하는 메서드
        ValueOperations<String,String> valueOperations=redisTemplate.opsForValue();
        valueOperations.set(key,value);
    }

    public String getValues(String key) { //지정된 키(key)에 해당하는 데이터를 Redis에서 가져오는 메서드
        ValueOperations<String,String> valueOperations=redisTemplate.opsForValue();
        return valueOperations.get(key);
    }

    public boolean existData(String key){ //지정된 키(key)에 해당하는 데이터가 Redis에 존재한다면 true를 반환하는 메서드
        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }

    public void setDataExpire(String key,String value,long duration){//지정된 키(key)에 값을 저장하고, 지정된 시간(duration) 후에 데이터를 만료시키도록 설정하는 메서드
        ValueOperations<String,String> valueOperations=redisTemplate.opsForValue();
        Duration expireDuration=Duration.ofSeconds(duration);
        valueOperations.set(key,value,expireDuration);
    }
    public void deleteData(String key){//지정된 키(key)에 해당하는 데이터를 Redis에서 삭제하는 메서드
        redisTemplate.delete(key);
    }
}
