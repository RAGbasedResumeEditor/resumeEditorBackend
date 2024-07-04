package com.team2.resumeeditorproject.admin.dto.response;

import lombok.Data;

import java.util.Map;

@Data
public class ProUserCountResponse {
    private Map<String, Object> proUserCount;

    public ProUserCountResponse(Map<String, Object> proUserCount) {
        this.proUserCount = proUserCount;
    }
}
