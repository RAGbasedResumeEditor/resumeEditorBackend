package com.team2.resumeeditorproject.admin.service;

import java.time.LocalDate;
import java.util.Date;
import java.util.Map;

public interface HistoryService {
    Map<String, Object> collectStatistics();
    void saveStatistics(Map<String, Object> statistics);

    Map<String, Object> getTotalTraffic();
    Map<String, Object> getProUserCnt();
    Map<String, Object> getTrafficForCurrentDate();
    Map<LocalDate, Integer> getTrafficData(LocalDate startDate, LocalDate endDate);
    Map<LocalDate, Integer> getDailyUserRegistrations(LocalDate startDate, LocalDate endDate);

    //Map<String, Object> getRNumForCurrentDate();
}
