package com.team2.resumeeditorproject.admin.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Builder
@Getter
public class RankByUserResponse {
    @JsonProperty("user_ranking")
    private Map<String, Integer> userRanking;
}
