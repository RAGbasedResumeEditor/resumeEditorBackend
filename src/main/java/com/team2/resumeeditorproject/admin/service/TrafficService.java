package com.team2.resumeeditorproject.admin.service;

import com.team2.resumeeditorproject.admin.domain.Traffic;
import com.team2.resumeeditorproject.admin.dto.TrafficDTO;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Map;

public interface TrafficService {
    void saveTraffic(TrafficDTO trafficDTO);
    Traffic updateTrafficForToday(LocalDate date);
    long getTotalVisitCount();
    long getVisitCountForToday();

    Map<YearMonth, Integer> getMonthlyTrafficData(LocalDate startDate, LocalDate endDate);
    void saveEditCountForToday();
}
