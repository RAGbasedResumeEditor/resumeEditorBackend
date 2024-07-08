package com.team2.resumeeditorproject.admin.service;

import com.team2.resumeeditorproject.admin.repository.AdminUserRepository;
import com.team2.resumeeditorproject.admin.repository.TrafficRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class UserStatisticsServiceImpl implements UserStatisticsService {

    private final AdminUserRepository adminUserRepository;
    private final TrafficRepository trafficRepository;

    @Override
    public int getUserCount() {
        return adminUserRepository.countUsers();
    }

    @Override
    public int getUserCountByGender(char gender) {
        return adminUserRepository.findByGender(gender).size();
    }

    @Override
    public int getUserCountByAgeGroup(int startAge, int endAge) {
        return adminUserRepository.findByAgeBetween(startAge, endAge).size();
    }

    @Override
    public int getUserCountByStatus(int status) {
        return adminUserRepository.findByStatus(status).size();
    }

    @Override
    public int getUserCountByMode(int mode) {
        return adminUserRepository.findByMode(mode).size();
    }

    @Override
    public int getUserCountByOccupation(String occupation) {
        return adminUserRepository.findByOccupation(occupation).size();
    }

    @Override
    public int getUserCountByWish(String wish) {
        return adminUserRepository.findByWish(wish).size();
    }

    @Override
    public int getProUserCount(int mode) {
        return adminUserRepository.findByMode(mode).size();
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
