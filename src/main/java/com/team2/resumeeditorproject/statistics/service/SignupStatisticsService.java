package com.team2.resumeeditorproject.statistics.service;

import com.team2.resumeeditorproject.common.util.DateRange;
import com.team2.resumeeditorproject.common.util.MonthRange;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Map;

public interface SignupStatisticsService {
    Map<LocalDate, Integer> getDailySignupStatistics(DateRange dateRange);
    Map<YearMonth, Integer> getMonthlySignupStatistics(MonthRange monthRange);
}
