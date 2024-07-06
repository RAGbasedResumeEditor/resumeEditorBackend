package com.team2.resumeeditorproject.admin.service;

import java.util.HashMap;
import java.util.Map;

import com.team2.resumeeditorproject.admin.dto.response.AgeCountResponse;
import com.team2.resumeeditorproject.admin.dto.response.GenderCountResponse;
import com.team2.resumeeditorproject.admin.dto.response.ModeCountResponse;
import com.team2.resumeeditorproject.admin.dto.response.OccupationCountResponse;
import com.team2.resumeeditorproject.admin.dto.response.ProUserCountResponse;
import com.team2.resumeeditorproject.admin.dto.response.StatusCountResponse;
import com.team2.resumeeditorproject.admin.dto.response.UserCountResponse;
import com.team2.resumeeditorproject.admin.dto.response.VisitTodayCountResponse;
import com.team2.resumeeditorproject.admin.dto.response.VisitTotalCountResponse;
import com.team2.resumeeditorproject.admin.dto.response.WishCountResponse;
import com.team2.resumeeditorproject.admin.repository.AdminUserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserStatisticsServiceImpl implements UserStatisticsService {

    private final AdminService adminService;
    private final HistoryService historyService;
    private final TrafficService trafficService;

    private final AdminUserRepository adminUserRepository;

    @Override
    public int getUserCount() {
        return adminUserRepository.countUsers();
    }

    @Override
    public int getUserCountByGender(char gender) {
        return adminUserRepository.findByGender(gender).size();
    }

    @Override
    public AgeCountResponse getAgeCount() {
        return new AgeCountResponse(adminService.getAgeCount());
    }

    @Override
    public StatusCountResponse getStatusCount() {
        return new StatusCountResponse(adminService.getStatusCount());
    }

    @Override
    public ModeCountResponse getModeCount() {
        return new ModeCountResponse(adminService.getModeCount());
    }

    @Override
    public OccupationCountResponse getOccupationCount(String occupation) {
        return new OccupationCountResponse(adminService.getOccupationCount(occupation));
    }

    @Override
    public WishCountResponse getWishCount(String wish) {
        return new WishCountResponse(adminService.getWishCount(wish));
    }

    @Override
    public ProUserCountResponse getProUserCount() {
        return new ProUserCountResponse(historyService.getProUserCount());
    }

    @Override
    public VisitTotalCountResponse getTotalVisitCount() {
        return new VisitTotalCountResponse(trafficService.getTotalVisitCount());
    }

    @Override
    public VisitTodayCountResponse getVisitTodayCount() {
        return new VisitTodayCountResponse(trafficService.getVisitCountForToday());
    }

}
