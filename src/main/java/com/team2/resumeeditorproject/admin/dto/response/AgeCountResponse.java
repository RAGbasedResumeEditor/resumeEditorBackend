package com.team2.resumeeditorproject.admin.dto.response;

import lombok.Data;

import java.util.Map;

@Data
public class AgeCountResponse {
    private Map<String, Object> ageCount;

    public AgeCountResponse(Map<String, Object> ageCount) {
        this.ageCount = ageCount;
    }
}
