package com.team2.resumeeditorproject.admin.service;

import com.team2.resumeeditorproject.admin.domain.Traffic;
import com.team2.resumeeditorproject.admin.dto.response.AccessDataResponse;
import com.team2.resumeeditorproject.admin.repository.TrafficRepository;
import com.team2.resumeeditorproject.common.util.DateRange;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Service
@RequiredArgsConstructor
public class AccessStatisticsServiceImpl implements AccessStatisticsService {

    private final TrafficRepository trafficRepository;

    @Override
    public AccessDataResponse getDailyAccessStatistics(DateRange dateRange) {
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
            return new AccessDataResponse(sortedDailyTrafficData);
        } catch (Exception exception) {
            throw new RuntimeException("Failed to fetch daily access statistics", exception);
        }
    }
}
