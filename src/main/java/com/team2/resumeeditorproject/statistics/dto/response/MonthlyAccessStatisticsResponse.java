package com.team2.resumeeditorproject.statistics.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.YearMonth;
import java.util.Map;

@Builder
@Getter
public class MonthlyAccessStatisticsResponse {
    private Map<YearMonth, Integer> trafficDate;
}
