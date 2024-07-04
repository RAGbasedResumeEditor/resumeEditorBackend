package com.team2.resumeeditorproject.admin.dto.response;

import lombok.Data;

import java.time.LocalDate;
import java.util.Map;

@Data
public class AccessDataResponse {
    private Map<LocalDate, Integer> trafficData;

    public AccessDataResponse(Map<LocalDate, Integer> trafficData) {
        this.trafficData = trafficData;
    }
}
