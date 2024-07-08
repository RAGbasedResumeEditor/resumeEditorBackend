package com.team2.resumeeditorproject.admin.controller;

import com.team2.resumeeditorproject.admin.dto.request.DailyStatisticsRequest;
import com.team2.resumeeditorproject.admin.dto.request.MonthlyStatisticsRequest;
import com.team2.resumeeditorproject.admin.dto.response.AgeCountResponse;
import com.team2.resumeeditorproject.admin.dto.response.DailyAccessStatisticsResponse;
import com.team2.resumeeditorproject.admin.dto.response.DailySignupStatisticsResponse;
import com.team2.resumeeditorproject.admin.dto.response.GenderCountResponse;
import com.team2.resumeeditorproject.admin.dto.response.ModeCountResponse;
import com.team2.resumeeditorproject.admin.dto.response.MonthlyAccessStatisticsResponse;
import com.team2.resumeeditorproject.admin.dto.response.MonthlySignupStatisticsResponse;
import com.team2.resumeeditorproject.admin.dto.response.OccupationCountResponse;
import com.team2.resumeeditorproject.admin.dto.response.ProUserCountResponse;
import com.team2.resumeeditorproject.admin.dto.response.StatusCountResponse;
import com.team2.resumeeditorproject.admin.dto.response.UserCountResponse;
import com.team2.resumeeditorproject.admin.dto.response.VisitTodayCountResponse;
import com.team2.resumeeditorproject.admin.dto.response.VisitTotalCountResponse;
import com.team2.resumeeditorproject.admin.dto.response.WishCountResponse;
import com.team2.resumeeditorproject.admin.service.AccessStatisticsService;
import com.team2.resumeeditorproject.admin.service.SignupStatisticsService;
import com.team2.resumeeditorproject.admin.service.UserStatisticsService;
import com.team2.resumeeditorproject.common.util.DateRange;
import com.team2.resumeeditorproject.common.util.MonthRange;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/statistics/user") // /admin 지우기
@RequiredArgsConstructor
public class UserStatisticsController {
    
    private final UserStatisticsService userStatisticsService;
    private final AccessStatisticsService accessStatisticsService;
    private final SignupStatisticsService signupStatisticsService;

    @GetMapping("/count")
    public ResponseEntity<UserCountResponse> getUserCount() {
        return ResponseEntity.ok()
                .body(UserCountResponse.builder()
                        .totalCount(userStatisticsService.getUserCount())
                        .build());
    }

    @GetMapping("/gender")
    public ResponseEntity<GenderCountResponse> getUserCountByGender() {
        return ResponseEntity.ok()
                .body(GenderCountResponse.builder()
                        .male(userStatisticsService.getUserCountByGender('M'))
                        .female(userStatisticsService.getUserCountByGender('F'))
                        .build());
    }

    @GetMapping("/age")
    public ResponseEntity<AgeCountResponse> getUserCountByAge() {
        return ResponseEntity.ok()
                .body(AgeCountResponse.builder()
                        .count20s(userStatisticsService.getUserCountByAgeGroup(20, 29))
                        .count30s(userStatisticsService.getUserCountByAgeGroup(30, 39))
                        .count40s(userStatisticsService.getUserCountByAgeGroup(40, 49))
                        .count50s(userStatisticsService.getUserCountByAgeGroup(50, 59))
                        .count60Plus(userStatisticsService.getUserCountByAgeGroup(60, Integer.MAX_VALUE))
                        .build());
    }

    @GetMapping("/status")
    public ResponseEntity<StatusCountResponse> getUserCountByStatus() {
        return ResponseEntity.ok()
                .body(StatusCountResponse.builder()
                        .status1(userStatisticsService.getUserCountByStatus(1))
                        .status2(userStatisticsService.getUserCountByStatus(2))
                        .build());
    }

    @GetMapping("/mode")
    public ResponseEntity<ModeCountResponse> getUserCountByMode() {
        return ResponseEntity.ok()
                .body(ModeCountResponse.builder()
                        .mode1(userStatisticsService.getUserCountByMode(1))
                        .mode2(userStatisticsService.getUserCountByMode(2))
                        .build());
    }

    @GetMapping("/occupation")
    public ResponseEntity<OccupationCountResponse> getUserCountByOccupation(@RequestParam(name = "occupation") String occupation) {
        return ResponseEntity.ok()
                .body(OccupationCountResponse.builder()
                        .occupation(occupation)
                        .count(userStatisticsService.getUserCountByOccupation(occupation))
                        .build());
    }

    @GetMapping("/wish")
    public ResponseEntity<WishCountResponse> getUserCountByWish(@RequestParam(name = "wish") String wish) {
        return ResponseEntity.ok()
                .body(WishCountResponse.builder()
                        .wish(wish)
                        .count(userStatisticsService.getUserCountByWish(wish))
                        .build());
    }

    @GetMapping("/pro")
    public ResponseEntity<ProUserCountResponse> getProUserCount() {
        return ResponseEntity.ok()
                .body(ProUserCountResponse.builder()
                        .pro(userStatisticsService.getProUserCount(2))
                        .build());
    }

    @GetMapping("/visitTotal")
    public ResponseEntity<VisitTotalCountResponse> getTotalVisitCount() {
        return ResponseEntity.ok()
                .body(VisitTotalCountResponse.builder()
                        .visitTotal(userStatisticsService.getTotalVisitCount())
                        .build());
    }

    @GetMapping("/visitToday")
    public ResponseEntity<VisitTodayCountResponse> getTodayVisitCount() {
        return ResponseEntity.ok()
                .body(VisitTodayCountResponse.builder()
                        .visitToday(userStatisticsService.getTodayVisitCount())
                        .build());
    }

    // 일별 접속자 집계
    @GetMapping("/access")
    public ResponseEntity<DailyAccessStatisticsResponse> getDailyAccessStatistics(
            @ModelAttribute DailyStatisticsRequest request) {
        DateRange dateRange = request.toDateRange();
        DailyAccessStatisticsResponse response = accessStatisticsService.getDailyAccessStatistics(dateRange);
        return ResponseEntity.ok(response);
    }

    // 월별 접속자 집계
    @GetMapping("/access/monthly")
    public ResponseEntity<MonthlyAccessStatisticsResponse> getMonthlyAccessStatistics(
            @ModelAttribute MonthlyStatisticsRequest request) {
        MonthRange monthRange = request.toMonthRange();
        MonthlyAccessStatisticsResponse response = accessStatisticsService.getMonthlyAccessStatistics(monthRange);
        return ResponseEntity.ok(response);
    }

    // 일별 회원가입 집계
    @GetMapping("/signup")
    public ResponseEntity<DailySignupStatisticsResponse> getDailySignupStatistics(
            @ModelAttribute DailyStatisticsRequest request) {
        DateRange dateRange = request.toDateRange();
        DailySignupStatisticsResponse response = signupStatisticsService.getDailySignupStatistics(dateRange);
        return ResponseEntity.ok(response);
    }

    // 월별 회원가입 집계
    @GetMapping("/signup/monthly")
    public ResponseEntity<MonthlySignupStatisticsResponse> getMonthlySignupStatistics(
            @ModelAttribute MonthlyStatisticsRequest request) {
        MonthRange monthRange = request.toMonthRange();
        MonthlySignupStatisticsResponse response = signupStatisticsService.getMonthlySignupStatistics(monthRange);
        return ResponseEntity.ok(response);
    }
}
