package com.team2.resumeeditorproject.admin.dto.response;

import lombok.Data;

@Data
public class VisitTotalCountResponse {
    private Long totalVisitCount;

    public VisitTotalCountResponse(Long totalVisitCount) {
        this.totalVisitCount = totalVisitCount;
    }
}
