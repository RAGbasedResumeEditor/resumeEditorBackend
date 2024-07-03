package com.team2.resumeeditorproject.admin.service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Map;

public interface HistoryService {
    void collectStatistics();

    Map<String, Object> getTotalVisitCount();
    Map<String, Object> getProUserCount();
    Map<LocalDate, Integer> getDailySignupUser(LocalDate startDate, LocalDate endDate);
    Map<LocalDate, Integer> getMonthlySignupUser(YearMonth yearMonth);
    Map<String, Object> getTotalEditCount();
    Map<String, Object> getEditCountForCurrentDate();

    Map<String, Object> getTotalBoardCount();

    Map<String, Object> getMonthlyEditStatistics();
    Map<String, Object> getWeeklyEditCount(String month);
    Map<String, Object> getDailyEditCount(String startDate, String endDate);
}
