package com.team2.resumeeditorproject.admin.dto.response;

import lombok.Data;

import java.time.LocalDate;
import java.util.Map;

@Data
public class DailyAccessStatisticsResponse {
    private Map<LocalDate, Integer> trafficData;

    public DailyAccessStatisticsResponse(Map<LocalDate, Integer> trafficData) {
        this.trafficData = trafficData;
    }
}
