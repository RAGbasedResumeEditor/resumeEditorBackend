package com.team2.resumeeditorproject.admin.service;

import java.util.Map;

//통계 관련 service
// 통계에 맞는 클래스명으로 변경 예정
public interface AdminService {
    Map<String, Object> getUserCount();
    Map<String, Object> getGenderCount();

    Map<String, Map<String, Integer>> getOccupationRank();
    Map<String, Map<String, Integer>> getCompanyRank();
    Map<String, Map<String, Integer>> getWishRank();

    Map<String, Object> getAgeCount();
    Map<String, Object> getStatusCount();
    Map<String, Object> getModeCount();

    Map<String, Object> getResumeEditCountByStatus();
    Map<String, Object> getResumeEditCountByAge();
    Map<String, Object> getResumeEditCountByMode();

    Map<String, Object> getMonthlyResumeEditCount();
    Map<String, Object> getDailyResumeEditCount();
    Map<String, Object> getWeeklyResumeEditCount();
}
