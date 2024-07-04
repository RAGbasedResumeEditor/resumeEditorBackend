package com.team2.resumeeditorproject.admin.dto.response;

import lombok.Data;

import java.time.YearMonth;
import java.util.Map;

@Data
public class MonthlyAccessDataResponse {
    private Map<YearMonth, Integer> trafficData;

    public MonthlyAccessDataResponse(Map<YearMonth, Integer> trafficData) {
        this.trafficData = trafficData;
    }
}
