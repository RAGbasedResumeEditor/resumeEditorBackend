package com.team2.resumeeditorproject.admin.controller;

import com.team2.resumeeditorproject.admin.service.AdminService;
import com.team2.resumeeditorproject.admin.service.HistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.*;

import static com.team2.resumeeditorproject.admin.service.ResponseHandler.createBadReqResponse;
import static com.team2.resumeeditorproject.admin.service.ResponseHandler.createResponse;

@RestController
@RequestMapping("/admin/history")
@RequiredArgsConstructor
public class HistoryController {

    private final HistoryService historyService;
    private final AdminService adminService;

    // 통계 수집
    @GetMapping("/collection")
    public ResponseEntity<Map<String, Object>> collectStatistics(){
        try {
            Map<String, Object> statistics = historyService.collectStatistics();
            historyService.saveStatistics(statistics);
            return ResponseEntity.ok(statistics);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", "An error occurred while collecting statistics.", "details", e.getMessage()));
        }
    }

    // response가져오는 로직 구현
    /*
    @GetMapping("/user")
    public ResponseEntity<Map<String, Object>> getUserStatistics(
            @RequestParam(value = "startDate", required = false) @DateTimeFormat(pattern = "yy-MM-dd") Optional<LocalDate> startDate,
            @RequestParam(value = "endDate", required = false) @DateTimeFormat(pattern = "yy-MM-dd") Optional<LocalDate> endDate,
            @RequestParam(value = "signupStartDate", required = false) @DateTimeFormat(pattern = "yy-MM-dd") Optional<LocalDate> signupStartDate,
            @RequestParam(value = "signupEndDate", required = false) @DateTimeFormat(pattern = "yy-MM-dd") Optional<LocalDate> signupEndDate){
        try {
            Map<String, Object> result = new LinkedHashMap<>();

            Map<String, Object> totalUsers = adminService.userCnt();
            Map<String, Object> modeCnt = adminService.modeCnt();
            double proRatio = (double) modeCnt.get("pro");
            Map<String, Object> totalTraffic = historyService.getTotalTraffic();
            Map<String, Object> todayTraffic = historyService.getTrafficForCurrentDate();

            Map<String, Object> statistics = historyService.collectStatistics();
            //Object todayTraffic = statistics.get("traffic"); // 이 부분 수정 예정
            Object userMode = statistics.get("user_mode");
            Object userStatus = statistics.get("user_status");
            Object userGender = statistics.get("user_gender");
            Object userAge = statistics.get("user_age");
            Object userOccu = statistics.get("user_occu");
            Object userComp = statistics.get("user_comp");
            Object userWish = statistics.get("user_wish");

            // 일별 접속자수 집계
            LocalDate start = startDate.orElse(LocalDate.now().minusDays(6)); // startDate가 주어지지 않으면 현재 날짜로부터 7일 전으로 설정
            LocalDate end = endDate.orElse(LocalDate.now()); // endDate가 주어지지 않으면 현재 날짜로 설정

            Map<LocalDate, Integer> trafficData = historyService.getTrafficData(start, end);

            // 트래픽 데이터가 없는 날짜에는 0을 설정
            for (LocalDate date = start; date.isBefore(end.plusDays(1)); date = date.plusDays(1)) {
                trafficData.putIfAbsent(date, 0);
            }

            Map<LocalDate, Integer> sortedTrafficData = new TreeMap<>(trafficData);
            // -------------------------

            // 일별 회원가입 집계
            LocalDate signupStart = signupStartDate.orElse(LocalDate.now().minusDays(6)); // startDate가 주어지지 않으면 현재 날짜로부터 7일 전으로 설정
            LocalDate signupEnd = signupEndDate.orElse(LocalDate.now()); // endDate가 주어지지 않으면 현재 날짜로 설정

            Map<LocalDate, Integer> signupData = historyService.getDailyUserRegistrations(signupStart, signupEnd);

            // 트래픽 데이터가 없는 날짜에는 0을 설정
            for (LocalDate date = signupStart; date.isBefore(end.plusDays(1)); date = date.plusDays(1)) {
                signupData.putIfAbsent(date, 0);
            }

            Map<LocalDate, Integer> sortedSignupData = new TreeMap<>(signupData);
            // -------------------------

            result.putAll(totalUsers);
            result.put("pro_ratio", proRatio);
            result.putAll(totalTraffic);
            result.putAll(todayTraffic);

            result.put("user_mode", userMode);
            result.put("user_status", userStatus);
            result.put("user_gender", userGender);
            result.put("user_age", userAge);
            result.put("user_occu", userOccu);
            result.put("user_comp", userComp);
            result.put("user_wish", userWish);
            result.put("traffic_data", sortedTrafficData);
            result.put("signup_data", sortedSignupData);

            return createResponse(result);
        }catch(Exception e){
            return createBadReqResponse(e.getMessage());
        }
    }
    */
    @GetMapping("/resume")
    public ResponseEntity<Map<String, Object>> getResumeStatistics(){
        Map<String, Object> result = new LinkedHashMap<>();

        Map<String, Object> statistics = historyService.collectStatistics();
        Object editCount = statistics.get("edit_count");

        //Map<String, Object> todayEditCount = historyService.getRNumForCurrentDate();

        result.put("edit_count", editCount);
        //result.putAll(todayEditCount);

        return createResponse(result);
    }
    //---------
}
