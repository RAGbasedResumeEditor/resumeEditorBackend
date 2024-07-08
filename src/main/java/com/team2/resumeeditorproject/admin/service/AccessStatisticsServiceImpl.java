package com.team2.resumeeditorproject.admin.service;

import com.team2.resumeeditorproject.admin.domain.Traffic;
import com.team2.resumeeditorproject.admin.dto.response.DailyAccessStatisticsResponse;
import com.team2.resumeeditorproject.admin.dto.response.MonthlyAccessStatisticsResponse;
import com.team2.resumeeditorproject.admin.repository.TrafficRepository;
import com.team2.resumeeditorproject.common.util.DateRange;
import com.team2.resumeeditorproject.common.util.MonthRange;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Service
@RequiredArgsConstructor
public class AccessStatisticsServiceImpl implements AccessStatisticsService {

    private final TrafficRepository trafficRepository;

    // 일별 접속자 집계
    @Override
    public DailyAccessStatisticsResponse getDailyAccessStatistics(DateRange dateRange) {
        Map<LocalDate, Integer> trafficData = new HashMap<>();
        List<LocalDate> dates = dateRange.getDates();
        try {
            List<Traffic> trafficList = trafficRepository.findByInDateBetween(dateRange.startDate(), dateRange.endDate());

            for (Traffic traffic : trafficList) {
                LocalDate date = traffic.getInDate();
                int visitCount = traffic.getVisitCount();
                trafficData.put(date, trafficData.getOrDefault(date, 0) + visitCount);
            }

            dates.forEach(date -> trafficData.putIfAbsent(date, 0));

            Map<LocalDate, Integer> sortedDailyTrafficData = new TreeMap<>(trafficData);
            return DailyAccessStatisticsResponse.builder()
                    .trafficData(sortedDailyTrafficData)
                    .build();
        } catch (Exception exception) {
            throw new RuntimeException("Failed to fetch daily access statistics", exception);
        }
    }

    // 월별 접속자 집계
    @Override
    public MonthlyAccessStatisticsResponse getMonthlyAccessStatistics(MonthRange monthRange) {
        Map<YearMonth, Integer> monthlyTrafficData = new HashMap<>();
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
                monthlyTrafficData.put(yearMonth, visitCount);
            }

            // 모든 월에 대해 접속자가 0인 경우에도 Map에 추가
            for (YearMonth yearMonth : months) {
                monthlyTrafficData.putIfAbsent(yearMonth, 0);
            }

            Map<YearMonth, Integer> sortedMonthlyTrafficData = new TreeMap<>(monthlyTrafficData);
            return new MonthlyAccessStatisticsResponse(sortedMonthlyTrafficData);

        } catch (Exception exception) {
            throw new RuntimeException("Failed to fetch monthly access statistics", exception);
        }
    }
}
