package com.team2.resumeeditorproject.admin.service;

import com.team2.resumeeditorproject.admin.dto.UserStatisticsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserStatisticsServiceImpl implements UserStatisticsService {

    private final AdminService adminService;
    private final HistoryService historyService;
    private final TrafficService trafficService;

    @Override
    public UserStatisticsResponse getUserStatistics(String group, String occupation, String wish) { // switch~case 분할 예정
        UserStatisticsResponse.UserStatisticsResponseBuilder responseBuilder = UserStatisticsResponse.builder();
        switch (group) {
            case "count":
                responseBuilder.userCount(adminService.getUserCount());
                break;
            case "gender":
                responseBuilder.genderCount(adminService.getGenderCount());
                break;
            case "age":
                responseBuilder.ageCount(adminService.getAgeCount());
                break;
            case "status":
                responseBuilder.statusCount(adminService.getStatusCount());
                break;
            case "mode":
                responseBuilder.modeCount(adminService.getModeCount());
                break;
            case "occupation":
                responseBuilder.occupationCount(adminService.getOccupationCount(occupation));
                break;
            case "wish":
                responseBuilder.wishCount(adminService.getWishCount(wish));
                break;
            case "pro":
                responseBuilder.proUserCount(historyService.getProUserCount());
                break;
            case "visitTotal":
                responseBuilder.totalVisitCount((long) trafficService.getTotalVisitCount());
                break;
            case "visitToday":
                responseBuilder.visitTodayCount((long) trafficService.getVisitCountForToday());
                break;
            default:
                throw new IllegalArgumentException("Invalid group parameter: " + group);
        }

        return responseBuilder.build();

    }
}
