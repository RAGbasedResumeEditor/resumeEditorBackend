package com.team2.resumeeditorproject.statistics.service;

import com.team2.resumeeditorproject.statistics.domain.Traffic;
import com.team2.resumeeditorproject.statistics.repository.TrafficRepository;
import com.team2.resumeeditorproject.common.util.DateRange;
import com.team2.resumeeditorproject.common.util.MonthRange;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccessStatisticsServiceImpl implements AccessStatisticsService {

    private final TrafficRepository trafficRepository;

    // 일별 접속자 집계
    @Override
    public Map<LocalDate, Integer> getDailyAccessStatistics(DateRange dateRange) {
        Map<LocalDate, Integer> trafficDate = new HashMap<>();
        List<LocalDate> dates = dateRange.getDates();
        try {
            List<Traffic> trafficList = trafficRepository.findByInDateBetween(dateRange.startDate(), dateRange.endDate());

            for (Traffic traffic : trafficList) {
                LocalDate date = traffic.getInDate();
                int visitCount = traffic.getVisitCount();
                trafficDate.put(date, trafficDate.getOrDefault(date, 0) + visitCount);
            }

            dates.forEach(date -> trafficDate.putIfAbsent(date, 0));

            return new TreeMap<>(trafficDate);
        } catch (Exception exception) {
            log.error("Failed to fetch daily access statistics", exception);
            throw new RuntimeException("Failed to fetch daily access statistics", exception);
        }
    }

    // 월별 접속자 집계
    @Override
    public Map<YearMonth, Integer> getMonthlyAccessStatistics(MonthRange monthRange) {
        Map<YearMonth, Integer> monthlyTrafficDate = new HashMap<>();
        try {
            List<YearMonth> months = monthRange.getMonths();
            LocalDate startDate = monthRange.startMonth().atDay(1); // 시작 월의 첫 날
            LocalDate endDate = monthRange.endMonth().atEndOfMonth(); // 종료 월의 마지막 날

            List<Traffic> trafficList = trafficRepository.findByInDateBetween(startDate, endDate);

            for (LocalDate date = startDate; date.isBefore(endDate.plusDays(1)); date = date.plusDays(1)) {
                YearMonth yearMonth = YearMonth.from(date);

                int visitCount = trafficList.stream()
                        .filter(traffic -> YearMonth.from(traffic.getInDate()).equals(yearMonth))
                        .mapToInt(Traffic::getVisitCount)
                        .sum();
                monthlyTrafficDate.put(yearMonth, visitCount);
            }

            for (YearMonth yearMonth : months) {
                monthlyTrafficDate.putIfAbsent(yearMonth, 0);
            }

            return new TreeMap<>(monthlyTrafficDate);
        } catch (Exception exception) {
            log.error("Failed to fetch monthly access statistics", exception);
            throw new RuntimeException("Failed to fetch monthly access statistics", exception);
        }
    }
}
