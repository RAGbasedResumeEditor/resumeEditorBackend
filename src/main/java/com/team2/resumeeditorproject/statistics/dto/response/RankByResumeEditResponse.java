package com.team2.resumeeditorproject.statistics.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Builder
@Getter
public class RankByResumeEditResponse {
    private Map<String, Integer> editRanking;
}
