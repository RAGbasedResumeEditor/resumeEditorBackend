package com.team2.resumeeditorproject.statistics.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class VisitTotalCountResponse {
    @JsonProperty("visit_total")
    private long visitTotal;
}
