package com.team2.resumeeditorproject.admin.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.concurrent.atomic.AtomicInteger;
/* 트래픽 데이터를 수집 */
@Component
public class TrafficInterceptor implements HandlerInterceptor {
    private final AtomicInteger trafficCounter = new AtomicInteger();

    public void incrementTrafficCount() {
        trafficCounter.incrementAndGet();
    }

    public int getTrafficCnt() {
        return trafficCounter.get();
    }

    public void resetTrafficCnt() {
        trafficCounter.set(0);
    }
}
