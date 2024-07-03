package com.team2.resumeeditorproject.admin.service;

import com.team2.resumeeditorproject.admin.dto.UserStatisticsResponse;

public interface UserStatisticsService {
    UserStatisticsResponse getUserStatistics(String group, String occupation, String wish);
}
