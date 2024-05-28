package com.team2.resumeeditorproject.admin.controller;

import com.team2.resumeeditorproject.admin.service.AdminService;
import com.team2.resumeeditorproject.admin.service.HistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.function.Function;

import static com.team2.resumeeditorproject.admin.service.ResponseHandler.*;

@Controller
@RequestMapping("/admin/stat")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;
    private final HistoryService historyService;

    /* 유저 정보에 관한 통계 */
    @GetMapping("/user")
    public ResponseEntity<Map<String,Object>> getUserCnt(@RequestParam(name="group", required=false) String group,
                                                         @RequestParam(name="occupation", required = false) String occupation,
                                                         @RequestParam(name="wish", required = false) String wish){
            Function<String, ResponseEntity<Map<String, Object>>> action = switch (group) {
                case "count" -> (g) -> createResponse(adminService.userCnt());
                case "gender" -> (g) -> createResponse(adminService.genderCnt());
                case "age" -> (g) -> createResponse(adminService.ageCnt());
                case "status" -> (g) -> createResponse(adminService.statusCnt());
                case "mode" -> (g) -> createResponse(adminService.modeCnt());
                case "occupation" -> (g) -> createResponse(adminService.occupCnt(occupation));
                case "wish" -> (g) -> createResponse(adminService.wishCnt(wish));
                case "pro" -> (g) -> createResponse(historyService.getProUserCnt());
                case "visitTotal" -> (g) -> createResponse(historyService.getTotalTraffic());
                case "visitToday" -> (g) -> createResponse(historyService.getTrafficForCurrentDate());
                default -> (g) ->  createBadReqResponse("잘못된 요청입니다.");
            };
            return action.apply(group);
    }

    // 일별 접속자 집계
    @GetMapping("/user/traffic")
    public ResponseEntity<Map<String,Object>> getTrafficStatistics(
            @RequestParam(value = "startDate", required = false) @DateTimeFormat(pattern = "yy-MM-dd") Optional<LocalDate> startDate,
            @RequestParam(value = "endDate", required = false) @DateTimeFormat(pattern = "yy-MM-dd") Optional<LocalDate> endDate){
        try {
            Map<String, Object> result = new LinkedHashMap<>();

            LocalDate start = startDate.orElse(LocalDate.now().minusDays(6)); // startDate가 주어지지 않으면 현재 날짜로부터 7일 전으로 설정
            LocalDate end = endDate.orElse(LocalDate.now()); // endDate가 주어지지 않으면 현재 날짜로 설정

            Map<LocalDate, Integer> trafficData = historyService.getTrafficData(start, end);

            // 트래픽 데이터가 없는 날짜에는 0을 설정
            for (LocalDate date = start; date.isBefore(end.plusDays(1)); date = date.plusDays(1)) {
                trafficData.putIfAbsent(date, 0);
            }

            Map<LocalDate, Integer> sortedTrafficData = new TreeMap<>(trafficData);

            result.put("traffic_data", sortedTrafficData);

            return createResponse(result);
        }catch(Exception e){
            return createBadReqResponse(e.getMessage());
        }
    }

    // 일별 회원가입 집계
    @GetMapping("/user/signup")
    public ResponseEntity<Map<String,Object>> getSignupStatistics(
            @RequestParam(value = "startDate", required = false) @DateTimeFormat(pattern = "yy-MM-dd") Optional<LocalDate> startDate,
            @RequestParam(value = "endDate", required = false) @DateTimeFormat(pattern = "yy-MM-dd") Optional<LocalDate> endDate){
        try {
            Map<String, Object> result = new LinkedHashMap<>();

            LocalDate start = startDate.orElse(LocalDate.now().minusDays(6)); // startDate가 주어지지 않으면 현재 날짜로부터 7일 전으로 설정
            LocalDate end = endDate.orElse(LocalDate.now()); // endDate가 주어지지 않으면 현재 날짜로 설정

            Map<LocalDate, Integer> signupData = historyService.getDailyUserRegistrations(start, end);

            // 트래픽 데이터가 없는 날짜에는 0을 설정
            for (LocalDate date = start; date.isBefore(end.plusDays(1)); date = date.plusDays(1)) {
                signupData.putIfAbsent(date, 0);
            }

            Map<LocalDate, Integer> sortedSignupData = new TreeMap<>(signupData);

            result.put("signup_data", sortedSignupData);

            return createResponse(result);
        }catch(Exception e){
            return createBadReqResponse(e.getMessage());
        }
    }

    /* 자소서 목록에 관한 통계 */
    @GetMapping("/board")
    public ResponseEntity<Map<String,Object>> getResumeStatByCompany(@RequestParam(name="group") String group,
                                                                     @RequestParam(name="company", required = false) String company,
                                                                     @RequestParam(name="occupation", required=false) String occupation){
            Function<String, ResponseEntity<Map<String, Object>>> action = switch (group) {
                case "company" -> (g) -> createResponse(adminService.CompResumeCnt(company));
                case "occupation" -> (g) -> createResponse(adminService.OccupResumeCnt(occupation));
                default -> (g) ->  createBadReqResponse("잘못된 요청입니다.");
            };
            return action.apply(group);
    }

    /* 자소서 첨삭 이용에 관한 통계 */
    @GetMapping("/resumeEdit")
    public ResponseEntity<Map<String, Object>> getResumeEditCountByStatus(@RequestParam(name="group", required = false) String group,
                                                                          @RequestParam(name="company", required = false) String company,
                                                                          @RequestParam(name="occupation", required = false) String occupation) {
            Function<String, ResponseEntity<Map<String, Object>>> action = switch (group) {
                case "status" -> (g) -> createResponse(adminService.resumeEditCntByStatus());
                case "company" -> (g) -> createResponse(adminService.resumeEditCntByComp(company));
                case "occupation" -> (g) -> createResponse(adminService.resumeEditCntByOccup(occupation));
                case "age" -> (g) -> createResponse(adminService.resumeEditCntByAge());
                case "mode" -> (g) -> createResponse(adminService.resumeEditCntByMode());
                case "monthly" -> (g) -> createResponse(adminService.resumeCntByMonth()); // 채용시즌(월별)
                case "weekly" -> (g) -> createResponse(adminService.resumeCntByWeekly()); // 채용시즌(주별)
                case "daily" -> (g) -> createResponse(adminService.resumeCntByDaily()); // 채용시즌(일별)
                default -> (g) -> createBadReqResponse("잘못된 요청입니다");
            };

            return action.apply(group);
    }

    @GetMapping("/rank/occupation")
    public ResponseEntity<Map<String, Object>> getOccupRank(){
        return createResponse(adminService.rankOccup());
    }

    @GetMapping("/rank/company")
    public ResponseEntity<Map<String, Object>> getCompRank(){
        return createResponse(adminService.rankComp());
    }
}
