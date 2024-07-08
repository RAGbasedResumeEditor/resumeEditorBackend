package com.team2.resumeeditorproject.admin.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.YearMonth;
import java.util.Map;

@Data
public class MonthlyAccessStatisticsResponse {
    @JsonProperty("traffic_data")
    private Map<YearMonth, Integer> trafficData;

    public MonthlyAccessStatisticsResponse(Map<YearMonth, Integer> trafficData) {
        this.trafficData = trafficData;
    }
}
