package com.team2.resumeeditorproject.admin.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.Map;

@Builder
@Getter
public class DailyAccessStatisticsResponse {
    private Map<LocalDate, Integer> trafficData;
}
