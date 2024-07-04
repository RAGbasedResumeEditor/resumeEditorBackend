package com.team2.resumeeditorproject.admin.dto.response;

import lombok.Data;

@Data
public class VisitTodayCountResponse {
    private Long visitTodayCount;

    public VisitTodayCountResponse(Long visitTodayCount) {
        this.visitTodayCount = visitTodayCount;
    }
}
