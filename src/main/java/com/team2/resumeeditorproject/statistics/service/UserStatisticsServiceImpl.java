package com.team2.resumeeditorproject.statistics.service;

import com.team2.resumeeditorproject.statistics.repository.TrafficRepository;
import com.team2.resumeeditorproject.statistics.repository.UserStatisticsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class UserStatisticsServiceImpl implements UserStatisticsService {

    private final UserStatisticsRepository userStatisticsRepository;
    private final TrafficRepository trafficRepository;

    @Override
    public int getUserCount() {
        return userStatisticsRepository.countUsers();
    }

    @Override
    public int getUserCountByGender(char gender) {
        return userStatisticsRepository.findByGender(gender).size();
    }

    @Override
    public int getUserCountByAgeGroup(int startAge, int endAge) {
        return userStatisticsRepository.findByAgeBetween(startAge, endAge).size();
    }

    @Override
    public int getUserCountByStatus(int status) {
        return userStatisticsRepository.findByStatus(status).size();
    }

    @Override
    public int getUserCountByMode(int mode) {
        return userStatisticsRepository.findByMode(mode).size();
    }

    @Override
    public int getProUserCount(int mode) {
        return userStatisticsRepository.findByMode(mode).size();
    }

    @Override
    public long getTotalVisitCount() {
        return trafficRepository.sumAllTraffic();
    }

    @Override
    public long getTodayVisitCount() {
        return trafficRepository.countByInDate(LocalDate.now());
    }

}
