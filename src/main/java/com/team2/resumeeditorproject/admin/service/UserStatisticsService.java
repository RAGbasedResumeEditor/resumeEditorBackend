package com.team2.resumeeditorproject.admin.service;

public interface UserStatisticsService {
    int getUserCount();
    int getUserCountByGender(char gender);
    int getUserCountByAgeGroup(int startAge, int endAge);
    int getUserCountByStatus(int status);
    int getUserCountByMode(int mode);
    int getUserCountByOccupation(String occupation);
    int getUserCountByWish(String wish);
    int getProUserCount(int mode);
    long getTotalVisitCount();
    long getTodayVisitCount();
}
