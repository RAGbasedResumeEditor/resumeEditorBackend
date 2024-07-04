package com.team2.resumeeditorproject.admin.dto.response;

import lombok.Data;

import java.util.Map;

@Data
public class StatusCountResponse {
    private Map<String, Object> statusCount;

    public StatusCountResponse(Map<String, Object> statusCount) {
        this.statusCount = statusCount;
    }
}
