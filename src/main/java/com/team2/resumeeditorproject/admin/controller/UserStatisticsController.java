package com.team2.resumeeditorproject.admin.controller;

import com.team2.resumeeditorproject.admin.dto.UserStatisticsResponse;
import com.team2.resumeeditorproject.admin.service.AdminService;
import com.team2.resumeeditorproject.admin.service.HistoryService;
import com.team2.resumeeditorproject.admin.service.TrafficService;
import com.team2.resumeeditorproject.admin.service.UserStatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
@RequestMapping("/admin/statistics") // /admin 지우기
@RequiredArgsConstructor
public class UserStatisticsController {

    private static final String Invalid_Request_Error_Message = "잘못된 요청입니다.";

    private final AdminService adminService;
    private final HistoryService historyService;
    private final TrafficService trafficService;
    private final UserStatisticsService userStatisticsService;

    // 유저 정보에 관한 통계
    @GetMapping("/user/{group}")
    public ResponseEntity<UserStatisticsResponse> getUserStatistics(@PathVariable String group,
                                                                @RequestParam(name="occupation", required = false) String occupation,
                                                                @RequestParam(name="wish", required = false) String wish) {
        try {
            UserStatisticsResponse response = userStatisticsService.getUserStatistics(group, occupation, wish);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    // 일별 접속자 집계
    @GetMapping("/user/access")
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
    @GetMapping("/user/access/monthly")
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
    @GetMapping("/user/signup")
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
    @GetMapping("/user/signup/monthly")
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
