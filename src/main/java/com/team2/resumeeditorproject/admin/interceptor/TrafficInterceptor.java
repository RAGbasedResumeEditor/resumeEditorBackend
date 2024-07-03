package com.team2.resumeeditorproject.admin.interceptor;

import com.team2.resumeeditorproject.admin.domain.Traffic;
import com.team2.resumeeditorproject.admin.repository.TrafficRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.time.LocalDate;
import java.util.concurrent.atomic.AtomicInteger;
/* 트래픽 데이터를 수집 */
@Component
@RequiredArgsConstructor
public class TrafficInterceptor implements HandlerInterceptor {
    private final AtomicInteger trafficCounter = new AtomicInteger();
    private final TrafficRepository trafficRepository;

    public void incrementTrafficCount() {
        Traffic traffic = trafficRepository.findByInDate(LocalDate.now());
        if (traffic == null) {
            traffic = new Traffic();
            traffic.setInDate(LocalDate.now());
        }
        traffic.setVisitCount(traffic.getVisitCount() + 1);
        trafficRepository.save(traffic);

        trafficCounter.incrementAndGet(); // 메모리 내 카운터도 증가
    }

    public int getTrafficCnt() {
        return trafficCounter.get();
    }

    public void resetTrafficCnt() {
        trafficCounter.set(0);
    }
}
