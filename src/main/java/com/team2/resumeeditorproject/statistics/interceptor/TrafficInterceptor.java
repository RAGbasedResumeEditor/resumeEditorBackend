package com.team2.resumeeditorproject.statistics.interceptor;

import com.team2.resumeeditorproject.statistics.domain.DailyStatistics;
import com.team2.resumeeditorproject.statistics.repository.DailyStatisticsRepository;
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
    private final DailyStatisticsRepository dailyStatisticsRepository;

    public void incrementTrafficCount() {
        DailyStatistics dailyStatistics = dailyStatisticsRepository.findByReferenceDate(LocalDate.now());
        if (dailyStatistics == null) {
            dailyStatistics = new DailyStatistics();
            dailyStatistics.setReferenceDate(LocalDate.now());
        }
        dailyStatistics.setVisitCount(dailyStatistics.getVisitCount() + 1);
        dailyStatisticsRepository.save(dailyStatistics);

        trafficCounter.incrementAndGet(); // 메모리 내 카운터도 증가
    }

    public int getTrafficCnt() {
        return trafficCounter.get();
    }

    public void resetTrafficCnt() {
        trafficCounter.set(0);
    }
}
