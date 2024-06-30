package com.team2.resumeeditorproject.admin.service;

import com.team2.resumeeditorproject.admin.domain.Traffic;
import com.team2.resumeeditorproject.admin.dto.TrafficDTO;

import java.time.LocalDate;
import java.util.Map;

public interface TrafficService {
    void saveTraffic(TrafficDTO trafficDTO);
    Traffic getTraffic(LocalDate date);
    long getTotalTraffic();
    long getTrafficForCurrentDate();
    Map<LocalDate, Integer> getTrafficData(LocalDate startDate, LocalDate endDate);
    Map<LocalDate, Integer> getMonthlyTrafficData(LocalDate startDate, LocalDate endDate);
    void updateEditCountForToday();

    //Map<YearMonth, Integer> getMonthlyUserRegistrations(LocalDate startDate, LocalDate endDate);
}
