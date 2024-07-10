package com.team2.resumeeditorproject.statistics.service;

public interface StatisticsCollectionService {
    String getUserCountByMode();
    String getUserCountByStatus();
    String getUserCountByGender();
    String getUserCountByAge();

    String getTopOccupationRanksByUsers();
    String getTopCompanyRanksByUsers();
    String getTopWishRanksByUsers();

    String getResumeEditCountByMode();
    String getResumeEditCountByStatus();
    String getResumeEditCountByAge();

    String getTopOccupationRanksByResumeEdits();
    String getTopCompanyRanksByResumeEdits();
}
