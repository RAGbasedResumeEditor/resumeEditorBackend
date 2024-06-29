package com.team2.resumeeditorproject.admin.service;

import com.team2.resumeeditorproject.admin.domain.Traffic;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Map;

public interface TrafficService {
    void saveTraffic(int visitCount, int editCount);
    Traffic getTraffic(LocalDate date);
    long getTotalTraffic();
    long getTrafficForCurrentDate();
    Map<LocalDate, Integer> getTrafficData(LocalDate startDate, LocalDate endDate);
    Map<LocalDate, Integer> getMonthlyTrafficData(LocalDate startDate, LocalDate endDate);
    void updateEditCountForToday();

    //Map<YearMonth, Integer> getMonthlyUserRegistrations(LocalDate startDate, LocalDate endDate);
}
