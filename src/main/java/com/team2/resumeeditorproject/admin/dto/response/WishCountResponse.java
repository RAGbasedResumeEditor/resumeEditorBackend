package com.team2.resumeeditorproject.admin.dto.response;

import lombok.Data;

import java.util.Map;

@Data
public class WishCountResponse {
    private Map<String, Object> wishCount;

    public WishCountResponse(Map<String, Object> wishCount) {
        this.wishCount = wishCount;
    }
}
