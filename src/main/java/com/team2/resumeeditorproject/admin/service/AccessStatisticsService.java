package com.team2.resumeeditorproject.admin.service;

import com.team2.resumeeditorproject.admin.dto.response.AccessDataResponse;
import com.team2.resumeeditorproject.common.util.DateRange;

public interface AccessStatisticsService {
    AccessDataResponse getDailyAccessStatistics(DateRange dateRange);
}
