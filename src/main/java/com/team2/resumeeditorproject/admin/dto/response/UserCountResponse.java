package com.team2.resumeeditorproject.admin.dto.response;

import lombok.Data;

import java.util.Map;

@Data
public class UserCountResponse {
    private Map<String, Object> userCount;

    public UserCountResponse(Map<String, Object> userCount) {
        this.userCount = userCount;
    }
}
