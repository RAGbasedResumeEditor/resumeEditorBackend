package com.team2.resumeeditorproject.admin.dto.response;

import lombok.Data;

import java.util.Map;

@Data
public class OccupationCountResponse {
    private Map<String, Object> occupationCount;

    public OccupationCountResponse(Map<String, Object> occupationCount) {
        this.occupationCount = occupationCount;
    }
}
