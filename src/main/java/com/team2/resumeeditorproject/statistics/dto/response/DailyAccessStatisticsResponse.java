package com.team2.resumeeditorproject.statistics.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.Map;

@Builder
@Getter
public class DailyAccessStatisticsResponse {
    @JsonProperty("traffic_date")
    private Map<LocalDate, Integer> trafficDate;
}
