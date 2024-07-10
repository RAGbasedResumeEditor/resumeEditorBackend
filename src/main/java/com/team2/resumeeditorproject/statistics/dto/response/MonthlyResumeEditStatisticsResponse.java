package com.team2.resumeeditorproject.statistics.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

import java.time.YearMonth;
import java.util.Map;

@Builder
@Getter
public class MonthlyResumeEditStatisticsResponse {
    @JsonProperty("edit_date")
    private Map<YearMonth, Integer> editDate;
}
