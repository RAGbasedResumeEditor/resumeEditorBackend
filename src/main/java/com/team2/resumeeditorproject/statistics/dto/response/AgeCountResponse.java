package com.team2.resumeeditorproject.statistics.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class AgeCountResponse {
    @JsonProperty("20s")
    private int count20s;

    @JsonProperty("30s")
    private int count30s;

    @JsonProperty("40s")
    private int count40s;

    @JsonProperty("50s")
    private int count50s;

    @JsonProperty("60Plus")
    private int count60Plus;
}
