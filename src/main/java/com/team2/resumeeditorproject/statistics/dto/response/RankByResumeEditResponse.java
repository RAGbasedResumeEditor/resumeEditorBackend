package com.team2.resumeeditorproject.statistics.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Builder
@Getter
public class RankByResumeEditResponse {
    @JsonProperty("edit_ranking")
    private Map<String, Integer> editRanking;
}
