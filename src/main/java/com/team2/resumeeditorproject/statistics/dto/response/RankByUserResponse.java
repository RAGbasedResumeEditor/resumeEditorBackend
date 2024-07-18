package com.team2.resumeeditorproject.statistics.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Builder
@Getter
public class RankByUserResponse {
    private Map<String, Integer> userRanking;
}
