package com.team2.resumeeditorproject.admin.dto.response;

import lombok.Data;

import java.time.LocalDate;
import java.util.Map;

@Data
public class DailySignupStatisticsResponse {
    private Map<LocalDate, Integer> signupData;

    public DailySignupStatisticsResponse(Map<LocalDate, Integer> signupData) {
        this.signupData = signupData;
    }
}
