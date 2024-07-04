package com.team2.resumeeditorproject.admin.dto.response;

import lombok.Data;

import java.time.YearMonth;
import java.util.Map;

@Data
public class MonthlyAccessStatisticsResponse {
    private Map<YearMonth, Integer> trafficData;

    public MonthlyAccessStatisticsResponse(Map<YearMonth, Integer> trafficData) {
        this.trafficData = trafficData;
    }
}
