package com.team2.resumeeditorproject.admin.service;

import com.team2.resumeeditorproject.admin.dto.response.AccessDataResponse;
import com.team2.resumeeditorproject.admin.dto.response.MonthlyAccessDataResponse;
import com.team2.resumeeditorproject.common.util.DateRange;
import com.team2.resumeeditorproject.common.util.MonthRange;

public interface AccessStatisticsService {
    AccessDataResponse getDailyAccessStatistics(DateRange dateRange);
    MonthlyAccessDataResponse getMonthlyAccessStatistics(MonthRange monthRange);
}
