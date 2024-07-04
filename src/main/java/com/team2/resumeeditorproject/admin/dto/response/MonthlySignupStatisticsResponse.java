package com.team2.resumeeditorproject.admin.dto.response;

import lombok.Data;

import java.time.YearMonth;
import java.util.Map;

@Data
public class MonthlySignupStatisticsResponse {
    private Map<YearMonth, Integer> signupData;

    public MonthlySignupStatisticsResponse(Map<YearMonth, Integer> signupData) {
        this.signupData = signupData;
    }
}
