package com.team2.resumeeditorproject.admin.dto.response;

import lombok.Data;

import java.util.Map;

@Data
public class GenderCountResponse {
    private Map<String, Object> genderCount;

    public GenderCountResponse(Map<String, Object> genderCount) {
        this.genderCount = genderCount;
    }
}
