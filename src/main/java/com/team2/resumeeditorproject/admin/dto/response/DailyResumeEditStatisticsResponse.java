package com.team2.resumeeditorproject.admin.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.Map;

@Builder
@Getter
public class DailyResumeEditStatisticsResponse {
    @JsonProperty("edit_date")
    private Map<LocalDate, Integer> editDate;
}
