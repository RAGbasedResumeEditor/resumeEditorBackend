package com.team2.resumeeditorproject.statistics.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.Map;

@Builder
@Getter
public class DailyResumeEditStatisticsResponse {
    private Map<LocalDate, Integer> editDate;
}
