package com.team2.resumeeditorproject.statistics.service;

import java.util.Map;

public interface RankStatisticsService {
    Map<String, Integer> getTopOccupationRanksByUsers();
    Map<String, Integer> getTopCompanyRanksByUsers();
    Map<String, Integer> getTopWishRanksByUsers();

    Map<String, Integer> getTopOccupationRanksByResumeEdits();
    Map<String, Integer> getTopCompanyRanksByResumeEdits();
}
