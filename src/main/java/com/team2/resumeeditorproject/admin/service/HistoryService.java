package com.team2.resumeeditorproject.admin.service;

import java.util.Map;

public interface HistoryService {
    void collectStatistics();

    Map<String, Object> getTotalVisitCount();

    Map<String, Object> getTotalEditCount();
    Map<String, Object> getEditCountForCurrentDate();

    Map<String, Object> getTotalBoardCount();

    Map<String, Object> getMonthlyEditStatistics();
    Map<String, Object> getWeeklyEditCount(String month);
    Map<String, Object> getDailyEditCount(String startDate, String endDate);
}
