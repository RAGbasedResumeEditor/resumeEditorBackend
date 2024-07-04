package com.team2.resumeeditorproject.admin.controller;

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
import com.team2.resumeeditorproject.admin.service.HistoryService;
import com.team2.resumeeditorproject.admin.service.TrafficService;
import com.team2.resumeeditorproject.admin.service.UserStatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

import static com.team2.resumeeditorproject.admin.service.ResponseHandler.createBadRequestResponse;
import static com.team2.resumeeditorproject.admin.service.ResponseHandler.createOkResponse;

@RestController
@RequestMapping("/admin/statistics/user") // /admin 지우기
@RequiredArgsConstructor
public class UserStatisticsController {

    private final HistoryService historyService;
    private final TrafficService trafficService;
    private final UserStatisticsService userStatisticsService;

    @GetMapping("/count")
    public ResponseEntity<UserCountResponse> getUserCount() {
        return ResponseEntity.ok(userStatisticsService.getUserCount());
    }

    @GetMapping("/gender")
    public ResponseEntity<GenderCountResponse> getGenderCount() {
        return ResponseEntity.ok(userStatisticsService.getGenderCount());
    }

    @GetMapping("/age")
    public ResponseEntity<AgeCountResponse> getAgeCount() {
        return ResponseEntity.ok(userStatisticsService.getAgeCount());
    }

    @GetMapping("/status")
    public ResponseEntity<StatusCountResponse> getStatusCount() {
        return ResponseEntity.ok(userStatisticsService.getStatusCount());
    }

    @GetMapping("/mode")
    public ResponseEntity<ModeCountResponse> getModeCount() {
        return ResponseEntity.ok(userStatisticsService.getModeCount());
    }

    @GetMapping("/occupation")
    public ResponseEntity<OccupationCountResponse> getOccupationCount(@RequestParam(name = "occupation") String occupation) {
        return ResponseEntity.ok(userStatisticsService.getOccupationCount(occupation));
    }

    @GetMapping("/wish")
    public ResponseEntity<WishCountResponse> getWishCount(@RequestParam(name = "wish") String wish) {
        return ResponseEntity.ok(userStatisticsService.getWishCount(wish));
    }

    @GetMapping("/pro")
    public ResponseEntity<ProUserCountResponse> getProUserCount() {
        return ResponseEntity.ok(userStatisticsService.getProUserCount());
    }

    @GetMapping("/visitTotal")
    public ResponseEntity<VisitTotalCountResponse> getTotalVisitCount() {
        return ResponseEntity.ok(userStatisticsService.getTotalVisitCount());
    }

    @GetMapping("/visitToday")
    public ResponseEntity<VisitTodayCountResponse> getVisitTodayCount() {
        return ResponseEntity.ok(userStatisticsService.getVisitTodayCount());
    }

    // 일별 접속자 집계
    @GetMapping("/access")
    public ResponseEntity<Map<String,Object>> getDailyAccessStatistics(
            @RequestParam(value = "startDate", required = false) @DateTimeFormat(pattern = "yy-MM-dd") Optional<LocalDate> startDate,
            @RequestParam(value = "endDate", required = false) @DateTimeFormat(pattern = "yy-MM-dd") Optional<LocalDate> endDate) {
        try {
            Map<String, Object> result = new LinkedHashMap<>();
            LocalDate currentDate = LocalDate.now();

            LocalDate start = startDate.orElse(currentDate.minusDays(6)); // startDate가 주어지지 않으면 현재 날짜로부터 7일 전으로 설정
            LocalDate end = endDate.orElse(currentDate); // endDate가 주어지지 않으면 현재 날짜로 설정

            Map<LocalDate, Integer> trafficData = trafficService.getTrafficData(start, end);

            result.put("traffic_data", trafficData);

            return createOkResponse(result);
        } catch (Exception e) {
            return createBadRequestResponse(e.getMessage());
        }
    }

    // 월별 접속자 집계
    @GetMapping("/access/monthly")
    public ResponseEntity<Map<String, Object>> getMonthlyAccessStatistics(
            @RequestParam(value = "startDate", required = false) @DateTimeFormat(pattern = "yyyy-MM") Optional<YearMonth> startDate) {
        try {
            Map<String, Object> result = new LinkedHashMap<>();

            YearMonth start = startDate.orElse(YearMonth.now()); // startDate가 주어지지 않으면 현재 달로 설정
            LocalDate startLocalDate = start.atDay(1);
            LocalDate endLocalDate = start.atEndOfMonth();

            Map<LocalDate, Integer> dailyTrafficData = trafficService.getTrafficData(startLocalDate, endLocalDate);

            Map<LocalDate, Integer> sortedDailyTrafficData = new TreeMap<>(dailyTrafficData);

            result.put("traffic_data", sortedDailyTrafficData);

            return createOkResponse(result);
        } catch (Exception e) {
            return createBadRequestResponse(e.getMessage());
        }
    }

    // 일별 회원가입 집계
    @GetMapping("/signup")
    public ResponseEntity<Map<String,Object>> getDailySignupStatistics(
            @RequestParam(value = "startDate", required = false) @DateTimeFormat(pattern = "yy-MM-dd") Optional<LocalDate> startDate,
            @RequestParam(value = "endDate", required = false) @DateTimeFormat(pattern = "yy-MM-dd") Optional<LocalDate> endDate) {
        try {
            Map<String, Object> result = new LinkedHashMap<>();

            LocalDate start = startDate.orElse(LocalDate.now().minusDays(6)); // startDate가 주어지지 않으면 현재 날짜로부터 7일 전으로 설정
            LocalDate end = endDate.orElse(LocalDate.now()); // endDate가 주어지지 않으면 현재 날짜로 설정

            Map<LocalDate, Integer> signupData = historyService.getDailySignupUser(start, end);

            // 트래픽 데이터가 없는 날짜에는 0을 설정
            for (LocalDate date = start; date.isBefore(end.plusDays(1)); date = date.plusDays(1)) {
                signupData.putIfAbsent(date, 0);
            }

            Map<LocalDate, Integer> sortedSignupData = new TreeMap<>(signupData);

            result.put("signup_data", sortedSignupData);

            return createOkResponse(result);
        } catch (Exception e) {
            return createBadRequestResponse(e.getMessage());
        }
    }

    // 월별 회원가입 집계
    @GetMapping("/signup/monthly")
    public ResponseEntity<Map<String, Object>> getMonthlySignupStatistics(
            @RequestParam(value = "startDate", required = false) @DateTimeFormat(pattern = "yyyy-MM") YearMonth yearMonth) {
        try {
            Map<String, Object> result = new LinkedHashMap<>();

            YearMonth selectedYearMonth = yearMonth != null ? yearMonth : YearMonth.now();

            Map<LocalDate, Integer> monthlySignupData = historyService.getMonthlySignupUser(selectedYearMonth);

            result.put("signup_data", monthlySignupData);

            return createOkResponse(result);
        } catch (Exception e) {
            return createBadRequestResponse(e.getMessage());
        }
    }
}
