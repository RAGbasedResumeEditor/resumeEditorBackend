package com.team2.resumeeditorproject.admin.service;

import com.team2.resumeeditorproject.admin.dto.response.DailyAccessStatisticsResponse;
import com.team2.resumeeditorproject.admin.dto.response.MonthlyAccessStatisticsResponse;
import com.team2.resumeeditorproject.common.util.DateRange;
import com.team2.resumeeditorproject.common.util.MonthRange;

public interface AccessStatisticsService {
    DailyAccessStatisticsResponse getDailyAccessStatistics(DateRange dateRange);
    MonthlyAccessStatisticsResponse getMonthlyAccessStatistics(MonthRange monthRange);
}
