package com.team2.resumeeditorproject.admin.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

import java.time.YearMonth;
import java.util.Map;

@Builder
@Getter
public class MonthlySignupStatisticsResponse {
    @JsonProperty("signup_date")
    private Map<YearMonth, Integer> signupDate;
}
