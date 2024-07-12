package com.team2.resumeeditorproject.statistics.service;

public interface UserStatisticsService {
    int getUserCount();
    int getUserCountByGender(char gender);
    int getUserCountByAgeGroup(int startAge, int endAge);
    int getUserCountByStatus(int status);
    int getUserCountByMode(int mode);
    int getProUserCount(int mode);
    long getTotalVisitCount();
    long getTodayVisitCount();
}
