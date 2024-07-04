package com.team2.resumeeditorproject.admin.service;

import com.team2.resumeeditorproject.admin.dto.response.DailySignupStatisticsResponse;
import com.team2.resumeeditorproject.admin.dto.response.MonthlySignupStatisticsResponse;
import com.team2.resumeeditorproject.common.util.DateRange;
import com.team2.resumeeditorproject.common.util.MonthRange;

public interface SignupStatisticsService {
    DailySignupStatisticsResponse getDailySignupStatistics(DateRange dateRange);
    MonthlySignupStatisticsResponse getMonthlySignupStatistics(MonthRange monthRange);
}
