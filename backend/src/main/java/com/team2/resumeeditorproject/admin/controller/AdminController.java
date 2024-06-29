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
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.function.Function;

// TODO : import할 때 와일드카드는 사용하지 않는 것이 좋음
import static com.team2.resumeeditorproject.admin.service.ResponseHandler.*;

// TODO : 통계가 admin의 하위역할이 맞는지 생각해볼 필요가 있음
// TODO : 통계결과를 전달하는데 AdminController는 목적과 명칭이 맞지 않음
// TODO : 클래스 크기가 커서 소규모 역할에 맞게 클래스를 분리하는게 좋아보임 /user, /rank 단위로
// TODO : 단문자, 약어사용은 지양, 약어를 꼭 사용하고싶다면 일관성있게
@Controller
@RequestMapping("/admin/stat")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;
    private final HistoryService historyService;
    private final TrafficService trafficService;


    // TODO : 불필요한 주석은 제거
    // TODO : 목적에 맞는 메서드명 사용
    // TODO : Map<String, Object>를 리턴하는 것은 지양하고 각각 DTO 클래스로 변경하는 것이 좋음
    // TODO : DTO로 변경시 메서드를 분할해야한다면 그게 맞음
    // TODO : 컨트롤러에서 비즈니스적인 분기문은 적절하지 않음, 차라리 service를 감싸는 service클래스를 하나 더 만드는 것이 적절해보임
    // TODO : parameter도 DTO로 받는 것이 적절해보임
    // TODO : createResponse로 감싸기 위해 Map을 사용한 듯 한데 안티패턴임 GetMapping을 /admin/stat/user/count, /admin/stat/user/gender 등으로 쪼개는게 맞아보임
    // TODO : createResponse는 createOkResponse처럼 명확한 이름으로 변경하는 것이 좋음
    // TODO : 왜 Cnt, Req만 약어인지?
    // TODO : 에러메시지도 상수로 관리, 또는 ENUM으로
    // TODO : ) {, ){, = 주변공백 일관성있게 수정
    // TODO : 아무리 단순한 코드이더라도 (g) 와 같은 단문자 변수는 지양
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

    // TODO : 위와 마찬가지로 파라미터, 리턴값은 DTO로 사용하는 것이 좋음
    // TODO : 일별 접속자 집계와 getTrafficStatistics 는 의미가 다른듯 함
    // TODO : 트래픽통계인지 접속자통계인지 명확히 할 필요가 있어보임, 트래픽통계라면 트래픽이 user 하위일 필요가 없고 접속자 통계라면 traffic 보다는 access가 더 적절해보임
    // TODO : exception의 메시지를 다이렉트로 api 사용자에게 전달하는 것은 좋은 방법이 아님
    // TODO : LocalDate.now()는 따로 빼는게 좋음, 미세한차이더라도 83라인의 NOW()와 84라인의 NOW()가 값이 다르므로
    // 일별 접속자 집계
    @GetMapping("/user/traffic")
    public ResponseEntity<Map<String,Object>> getTrafficStatistics(
            @RequestParam(value = "startDate", required = false) @DateTimeFormat(pattern = "yy-MM-dd") Optional<LocalDate> startDate,
            @RequestParam(value = "endDate", required = false) @DateTimeFormat(pattern = "yy-MM-dd") Optional<LocalDate> endDate){
        try {
            Map<String, Object> result = new LinkedHashMap<>();

            LocalDate start = startDate.orElse(LocalDate.now().minusDays(6)); // startDate가 주어지지 않으면 현재 날짜로부터 7일 전으로 설정
            LocalDate end = endDate.orElse(LocalDate.now()); // endDate가 주어지지 않으면 현재 날짜로 설정

            Map<LocalDate, Integer> trafficData = trafficService.getTrafficData(start, end);

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

    // TODO : 컨트롤러에서 비즈니스로직을 전개하는 것은 지양
    // 월별 접속자 집계
    @GetMapping("/user/traffic/monthly")
    public ResponseEntity<Map<String, Object>> getMonthlyTrafficStatistics(
            @RequestParam(value = "startDate", required = false) @DateTimeFormat(pattern = "yyyy-MM") Optional<YearMonth> startDate){
        try {
            Map<String, Object> result = new LinkedHashMap<>();

            YearMonth start = startDate.orElse(YearMonth.now()); // startDate가 주어지지 않으면 현재 달로 설정
            LocalDate startLocalDate = start.atDay(1);
            LocalDate endLocalDate = start.atEndOfMonth();

            Map<LocalDate, Integer> dailyTrafficData = trafficService.getTrafficData(startLocalDate, endLocalDate);

            // 트래픽 데이터가 없는 날짜에는 0을 설정
            for (LocalDate date = startLocalDate; !date.isAfter(endLocalDate); date = date.plusDays(1)) {
                dailyTrafficData.putIfAbsent(date, 0);
            }

            Map<LocalDate, Integer> sortedDailyTrafficData = new TreeMap<>(dailyTrafficData);

            result.put("traffic_data", sortedDailyTrafficData);

            return createResponse(result);
        } catch (Exception e) {
            return createBadReqResponse(e.getMessage());
        }
    }

    // TODO : rest api 설계시 규칙들이 있는데 찾아보면 좋을 듯
    // TODO : rest api를 염두하지 않는 설계더라도 url 규칙은 지키는 게 좋음
    // TODO : } catch (Exception exception) { 공백은 지키는게 좋음
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

    // 월별 회원가입 집계
    @GetMapping("/user/signup/monthly")
    public ResponseEntity<Map<String, Object>> getMonthlySignupStatistics(
            @RequestParam(value = "startDate", required = false) @DateTimeFormat(pattern = "yyyy-MM") YearMonth yearMonth) {
        try {
            Map<String, Object> result = new LinkedHashMap<>();

            YearMonth selectedYearMonth = yearMonth != null ? yearMonth : YearMonth.now();

            Map<LocalDate, Integer> monthlySignupData = historyService.getMonthlyUserRegistrations(selectedYearMonth);

            result.put("signup_data", monthlySignupData);

            return createResponse(result);
        } catch (Exception e) {
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


    // TODO : url에 대문자는 사용하지 않는 것이 좋음 /resume-edit
    /* 자소서 첨삭 이용에 관한 통계 */
    @GetMapping("/resumeEdit")
    public ResponseEntity<Map<String, Object>> getResumeEditCountByStatus(@RequestParam(name="group", required = false) String group,
                                                                          @RequestParam(name="company", required = false) String company,
                                                                          @RequestParam(name="occupation", required = false) String occupation
                                                                          ) {
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

    // 관리자 - 통계 페이지 내에서 각 영역 top 5를 출력한다.
    @GetMapping("/rank/occupation")
    public ResponseEntity<Map<String, Object>> getOccupRank(){
        return createResponse(adminService.rankOccup());
    }

    @GetMapping("/rank/company")
    public ResponseEntity<Map<String, Object>> getCompRank(){
        return createResponse(adminService.rankComp());
    }

    @GetMapping("/rank/wish")
    public ResponseEntity<Map<String, Object>> getWishRank(){
        return createResponse(adminService.rankWish());
    }

    // 자소서 통계
    @GetMapping("/resume/count")
    public ResponseEntity<Map<String,Object>> getResumeStat(@RequestParam(name="group", required = false) String group){
        Function<String, ResponseEntity<Map<String, Object>>> action = switch (group) {
            case "editTotal" -> (g) -> createResponse(historyService.getTotalEdit());
            case "editToday" -> (g) -> createResponse(historyService.getRNumForCurrentDate());
            case "boardToday" -> (g) -> createResponse(historyService.getTotalBoardCnt());
            default -> (g) -> createBadReqResponse("잘못된 요청입니다");
        };

        return action.apply(group);
    }

    @GetMapping("/resume/monthly")
    public ResponseEntity<Map<String,Object>> getEditStatByMonthly(){
        try {
            Map<String, Object> result = new LinkedHashMap<>();

            result.put("edit_monthly", historyService.getEditByMonthly());

            return createResponse(result);
        }catch (Exception e){
            return createBadReqResponse(e.getMessage());
        }
    }

    // TODO : 위도 마찬가지지만 e 같은 단문자 사용은 지양
    // TODO : catch 에서 하는일이 동일한데 IllegalArgumentException과 Exception을 나눠야하는지?
    @GetMapping("/resume/weekly")
    public ResponseEntity<Map<String,Object>> getEditStatByWeekly(@RequestParam(name="month", required = false) String month){
        try {
            return createResponse(historyService.getEditByWeekly(month));
        } catch (IllegalArgumentException e) {
            return createBadReqResponse(e.getMessage());
        } catch (Exception e) {
            return createBadReqResponse(e.getMessage());
        }
    }

    @GetMapping("/resume/daily")
    public ResponseEntity<Map<String,Object>> getEditStatByDaily(@RequestParam(value = "startDate", required = false) String startDate,
                                                                 @RequestParam(value = "endDate", required = false)  String endDate){
        try {
            Map<String, Object> result = new LinkedHashMap<>();

            Map<String, Object> editDaily = historyService.getEditByDaily(startDate, endDate);
            result.put("edit_daily", editDaily);
            return createResponse(result);
        } catch (Exception e) {
            return createBadReqResponse(e.getMessage());
        }
    }
}
