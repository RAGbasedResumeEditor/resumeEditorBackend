package com.team2.resumeeditorproject.admin.service;

import com.team2.resumeeditorproject.common.util.DateRange;
import com.team2.resumeeditorproject.common.util.MonthRange;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Map;

public interface AccessStatisticsService {
    Map<LocalDate, Integer> getDailyAccessStatistics(DateRange dateRange);
    Map<YearMonth, Integer> getMonthlyAccessStatistics(MonthRange monthRange);
}
