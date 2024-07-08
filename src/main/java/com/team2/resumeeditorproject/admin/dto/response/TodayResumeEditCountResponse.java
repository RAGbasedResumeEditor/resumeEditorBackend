package com.team2.resumeeditorproject.admin.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class TodayResumeEditCountResponse {
    @JsonProperty("today_edit")
    private long todayEdit;
}
