package com.team2.resumeeditorproject.admin.service;

public interface ResumeStatisticsService {
    long getTotalResumeEditCount();
    int getTodayResumeEditCount();
    long getTotalResumeBoardCount();
    int getResumeEditCountByStatus(int status);
    int getResumeEditCountByAge(int startAge, int endAge);
    int getResumeEditCountByMode(int mode);
}
