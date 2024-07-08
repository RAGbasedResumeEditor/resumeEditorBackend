package com.team2.resumeeditorproject.admin.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDate;
import java.util.Map;

@Data
public class DailySignupStatisticsResponse {
    @JsonProperty("signup_date")
    private Map<LocalDate, Integer> signupData;

    public DailySignupStatisticsResponse(Map<LocalDate, Integer> signupData) {
        this.signupData = signupData;
    }
}
