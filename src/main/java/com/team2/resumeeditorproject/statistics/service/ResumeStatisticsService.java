package com.team2.resumeeditorproject.statistics.service;

import com.team2.resumeeditorproject.common.util.DateRange;
import com.team2.resumeeditorproject.common.util.MonthRange;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Map;

public interface ResumeStatisticsService {
    long getTotalResumeEditCount();
    int getTodayResumeEditCount();
    long getTotalResumeBoardCount();
    int getResumeEditCountByStatus(int status);
    int getResumeEditCountByAgeGroup(int startAge, int endAge);
    int getResumeEditCountByMode(int mode);
    Map<LocalDate, Integer> getDailyResumeEditStatistics(DateRange dateRange);
    Map<YearMonth, Integer> getMonthlyResumeEditStatistics(MonthRange monthRange);
}
