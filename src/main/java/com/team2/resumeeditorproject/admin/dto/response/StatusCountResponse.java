package com.team2.resumeeditorproject.admin.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class StatusCountResponse {
    @JsonProperty("status_1")
    private int status1;

    @JsonProperty("status_2")
    private int status2;
}
