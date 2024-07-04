package com.team2.resumeeditorproject.admin.service;

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

public interface UserStatisticsService {
    UserCountResponse getUserCount();
    GenderCountResponse getGenderCount();
    AgeCountResponse getAgeCount();
    StatusCountResponse getStatusCount();
    ModeCountResponse getModeCount();
    OccupationCountResponse getOccupationCount(String occupation);
    WishCountResponse getWishCount(String wish);
    ProUserCountResponse getProUserCount();
    VisitTotalCountResponse getTotalVisitCount();
    VisitTodayCountResponse getVisitTodayCount();
}
