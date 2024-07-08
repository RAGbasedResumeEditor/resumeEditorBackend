package com.team2.resumeeditorproject.admin.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.YearMonth;
import java.util.Map;

@Data
public class MonthlySignupStatisticsResponse {
    @JsonProperty("signup_data")
    private Map<YearMonth, Integer> signupData;

    public MonthlySignupStatisticsResponse(Map<YearMonth, Integer> signupData) {
        this.signupData = signupData;
    }
}
