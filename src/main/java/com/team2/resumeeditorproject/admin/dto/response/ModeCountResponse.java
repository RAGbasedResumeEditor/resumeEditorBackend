package com.team2.resumeeditorproject.admin.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ModeCountResponse {
    @JsonProperty("mode_1")
    private int mode1;

    @JsonProperty("mode_2")
    private int mode2;
}
