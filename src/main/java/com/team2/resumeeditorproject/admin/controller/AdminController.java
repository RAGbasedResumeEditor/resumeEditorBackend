package com.team2.resumeeditorproject.admin.controller;

import com.team2.resumeeditorproject.admin.service.AdminService;
import com.team2.resumeeditorproject.admin.service.HistoryService;
import com.team2.resumeeditorproject.admin.service.TrafficService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.function.Function;

import static com.team2.resumeeditorproject.admin.service.ResponseHandler.createOkResponse;
import static com.team2.resumeeditorproject.admin.service.ResponseHandler.createBadRequestResponse;

@Controller
@RequestMapping("/admin/stat")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;
    private final HistoryService historyService;
    private final TrafficService trafficService;

    /* 유저 정보에 관한 통계 */
    @GetMapping("/user")
    public ResponseEntity<Map<String,Object>> getUserCnt(@RequestParam(name="group", required=false) String group,
                                                         @RequestParam(name="occupation", required = false) String occupation,
                                                         @RequestParam(name="wish", required = false) String wish){
            Function<String, ResponseEntity<Map<String, Object>>> action = switch (group) {
                case "count" -> (g) -> createOkResponse(adminService.userCnt());
                case "gender" -> (g) -> createOkResponse(adminService.genderCnt());
                case "age" -> (g) -> createOkResponse(adminService.ageCnt());
                case "status" -> (g) -> createOkResponse(adminService.statusCnt());
                case "mode" -> (g) -> createOkResponse(adminService.modeCnt());
                case "occupation" -> (g) -> createOkResponse(adminService.occupCnt(occupation));
                case "wish" -> (g) -> createOkResponse(adminService.wishCnt(wish));
                case "pro" -> (g) -> createOkResponse(historyService.getProUserCnt());
                case "visitTotal" -> (g) -> createOkResponse(historyService.getTotalTraffic());
                case "visitToday" -> (g) -> createOkResponse(historyService.getTrafficForCurrentDate());
                default -> (g) ->  createBadRequestResponse("잘못된 요청입니다.");
            };
            return action.apply(group);
    }

    // 일별 접속자 집계
    @GetMapping("/user/access")
    public ResponseEntity<Map<String,Object>> getAccessCount(
            @RequestParam(value = "startDate", required = false) @DateTimeFormat(pattern = "yy-MM-dd") Optional<LocalDate> startDate,
            @RequestParam(value = "endDate", required = false) @DateTimeFormat(pattern = "yy-MM-dd") Optional<LocalDate> endDate){
        try {
            Map<String, Object> result = new LinkedHashMap<>();

            LocalDate start = startDate.orElse(LocalDate.now().minusDays(6)); // startDate가 주어지지 않으면 현재 날짜로부터 7일 전으로 설정
            LocalDate end = endDate.orElse(LocalDate.now()); // endDate가 주어지지 않으면 현재 날짜로 설정

            Map<LocalDate, Integer> trafficData = trafficService.getTrafficData(start, end);

            result.put("traffic_data", trafficData);

            return createOkResponse(result);
        }catch(Exception exception){
            return createBadRequestResponse(exception.getMessage());
        }
    }

    // 월별 접속자 집계
    @GetMapping("/user/access/monthly")
    public ResponseEntity<Map<String, Object>> getMonthlyAccessCount(
            @RequestParam(value = "startDate", required = false) @DateTimeFormat(pattern = "yyyy-MM") Optional<YearMonth> startDate){
        try {
            Map<String, Object> result = new LinkedHashMap<>();

            YearMonth start = startDate.orElse(YearMonth.now()); // startDate가 주어지지 않으면 현재 달로 설정
            LocalDate startLocalDate = start.atDay(1);
            LocalDate endLocalDate = start.atEndOfMonth();

            Map<LocalDate, Integer> dailyTrafficData = trafficService.getTrafficData(startLocalDate, endLocalDate);

            Map<LocalDate, Integer> sortedDailyTrafficData = new TreeMap<>(dailyTrafficData);

            result.put("traffic_data", sortedDailyTrafficData);

            return createOkResponse(result);
        } catch (Exception exception) {
            return createBadRequestResponse(exception.getMessage());
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

            return createOkResponse(result);
        }catch(Exception exception){
            return createBadRequestResponse(exception.getMessage());
        }
    }

    // 월별 회원가입 집계
    @GetMapping("/user/signup/monthly")
    public ResponseEntity<Map<String, Object>> getMonthlySignupStatistics(
            @RequestParam(value = "startDate", required = false) @DateTimeFormat(pattern = "yyyy-MM") YearMonth yearMonth) {
        try {
            Map<String, Object> result = new LinkedHashMap<>();

            YearMonth selectedYearMonth = yearMonth != null ? yearMonth : YearMonth.now();

            Map<LocalDate, Integer> monthlySignupData = historyService.getMonthlyUserRegistrations(selectedYearMonth);

            result.put("signup_data", monthlySignupData);

            return createOkResponse(result);
        } catch (Exception exception) {
            return createBadRequestResponse(exception.getMessage());
        }
    }

    /* 자소서 목록에 관한 통계 */
    @GetMapping("/board")
    public ResponseEntity<Map<String,Object>> getResumeStatByCompany(@RequestParam(name="group") String group,
                                                                     @RequestParam(name="company", required = false) String company,
                                                                     @RequestParam(name="occupation", required=false) String occupation){
            Function<String, ResponseEntity<Map<String, Object>>> action = switch (group) {
                case "company" -> (g) -> createOkResponse(adminService.CompResumeCnt(company));
                case "occupation" -> (g) -> createOkResponse(adminService.OccupResumeCnt(occupation));
                default -> (g) ->  createBadRequestResponse("잘못된 요청입니다.");
            };
            return action.apply(group);
    }

    /* 자소서 첨삭 이용에 관한 통계 */
    @GetMapping("/resumeEdit")
    public ResponseEntity<Map<String, Object>> getResumeEditCountByStatus(@RequestParam(name="group", required = false) String group,
                                                                          @RequestParam(name="company", required = false) String company,
                                                                          @RequestParam(name="occupation", required = false) String occupation
                                                                          ) {
            Function<String, ResponseEntity<Map<String, Object>>> action = switch (group) {
                case "status" -> (g) -> createOkResponse(adminService.resumeEditCntByStatus());
                case "company" -> (g) -> createOkResponse(adminService.resumeEditCntByComp(company));
                case "occupation" -> (g) -> createOkResponse(adminService.resumeEditCntByOccup(occupation));
                case "age" -> (g) -> createOkResponse(adminService.resumeEditCntByAge());
                case "mode" -> (g) -> createOkResponse(adminService.resumeEditCntByMode());
                case "monthly" -> (g) -> createOkResponse(adminService.resumeCntByMonth()); // 채용시즌(월별)
                case "weekly" -> (g) -> createOkResponse(adminService.resumeCntByWeekly()); // 채용시즌(주별)
                case "daily" -> (g) -> createOkResponse(adminService.resumeCntByDaily()); // 채용시즌(일별)
                default -> (g) -> createBadRequestResponse("잘못된 요청입니다");
            };
            return action.apply(group);
    }

    // 관리자 - 통계 페이지 내에서 각 영역 top 5를 출력한다.
    @GetMapping("/rank/occupation")
    public ResponseEntity<Map<String, Object>> getOccupRank(){
        return createOkResponse(adminService.rankOccup());
    }

    @GetMapping("/rank/company")
    public ResponseEntity<Map<String, Object>> getCompRank(){
        return createOkResponse(adminService.rankComp());
    }

    @GetMapping("/rank/wish")
    public ResponseEntity<Map<String, Object>> getWishRank(){
        return createOkResponse(adminService.rankWish());
    }

    // 자소서 통계
    @GetMapping("/resume/count")
    public ResponseEntity<Map<String,Object>> getResumeStat(@RequestParam(name="group", required = false) String group){
        Function<String, ResponseEntity<Map<String, Object>>> action = switch (group) {
            case "editTotal" -> (g) -> createOkResponse(historyService.getTotalEdit());
            case "editToday" -> (g) -> createOkResponse(historyService.getRNumForCurrentDate());
            case "boardToday" -> (g) -> createOkResponse(historyService.getTotalBoardCnt());
            default -> (g) -> createBadRequestResponse("잘못된 요청입니다");
        };

        return action.apply(group);
    }

    @GetMapping("/resume/monthly")
    public ResponseEntity<Map<String,Object>> getEditStatByMonthly(){
        try {
            Map<String, Object> result = new LinkedHashMap<>();

            result.put("edit_monthly", historyService.getEditByMonthly());

            return createOkResponse(result);
        }catch (Exception exception){
            return createBadRequestResponse(exception.getMessage());
        }
    }

    @GetMapping("/resume/weekly")
    public ResponseEntity<Map<String,Object>> getEditStatByWeekly(@RequestParam(name="month", required = false) String month){
        try {
            return createOkResponse(historyService.getEditByWeekly(month));
        } catch (Exception exception) {
            return createBadRequestResponse(exception.getMessage());
        }
    }

    @GetMapping("/resume/daily")
    public ResponseEntity<Map<String,Object>> getEditStatByDaily(@RequestParam(value = "startDate", required = false) String startDate,
                                                                 @RequestParam(value = "endDate", required = false)  String endDate){
        try {
            Map<String, Object> result = new LinkedHashMap<>();

            Map<String, Object> editDaily = historyService.getEditByDaily(startDate, endDate);
            result.put("edit_daily", editDaily);
            return createOkResponse(result);
        } catch (Exception exception) {
            return createBadRequestResponse(exception.getMessage());
        }
    }
}
