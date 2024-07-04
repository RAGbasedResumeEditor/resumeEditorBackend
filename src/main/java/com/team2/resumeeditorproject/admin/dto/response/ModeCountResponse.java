package com.team2.resumeeditorproject.admin.dto.response;

import lombok.Data;

import java.util.Map;

@Data
public class ModeCountResponse {
    private Map<String, Object> modeCount;

    public ModeCountResponse(Map<String, Object> modeCount) {
        this.modeCount = modeCount;
    }
}
