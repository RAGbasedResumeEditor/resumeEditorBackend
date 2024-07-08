package com.team2.resumeeditorproject.admin.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class TotalResumeBoardCountResponse {
    @JsonProperty("total_board")
    private long totalBoard;
}
