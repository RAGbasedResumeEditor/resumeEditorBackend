package com.team2.resumeeditorproject.admin.controller;

import com.team2.resumeeditorproject.admin.dto.response.TodayResumeEditCountResponse;
import com.team2.resumeeditorproject.admin.dto.response.TotalResumeBoardCountResponse;
import com.team2.resumeeditorproject.admin.dto.response.TotalResumeEditCountResponse;
import com.team2.resumeeditorproject.admin.service.AdminService;
import com.team2.resumeeditorproject.admin.service.HistoryService;
import com.team2.resumeeditorproject.admin.service.ResumeStatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;

import static com.team2.resumeeditorproject.common.util.ResponseHandler.createBadRequestResponse;
import static com.team2.resumeeditorproject.common.util.ResponseHandler.createOkResponse;

@Controller
@RequestMapping("/admin/statistics") // /admin 지우기
@RequiredArgsConstructor
public class ResumeStatisticsController {

    private static final String Invalid_Request_Error_Message = "잘못된 요청입니다.";

    private final AdminService adminService;
    private final HistoryService historyService;

    private final ResumeStatisticsService resumeStatisticsService;

    /* 자소서 목록에 관한 통계 */
    @GetMapping("/board")
    public ResponseEntity<Map<String,Object>> getResumeBoardStatistics(@RequestParam(name="group") String group,
                                                                       @RequestParam(name="company", required = false) String company,
                                                                       @RequestParam(name="occupation", required=false) String occupation) {
        Function<String, ResponseEntity<Map<String, Object>>> action = switch (group) {
            case "company" -> (g) -> createOkResponse(adminService.getResumeCountByCompany(company));
            case "occupation" -> (g) -> createOkResponse(adminService.getResumeCountByOccupation(occupation));
            default -> (g) ->  createBadRequestResponse(Invalid_Request_Error_Message);
        };
        return action.apply(group);
    }

    /* 자소서 첨삭 이용에 관한 통계 */
    @GetMapping("/resume-edit")
    public ResponseEntity<Map<String, Object>> getResumeEditStatistics(@RequestParam(name="group", required = false) String group,
                                                                       @RequestParam(name="company", required = false) String company,
                                                                       @RequestParam(name="occupation", required = false) String occupation
    ) {
        Function<String, ResponseEntity<Map<String, Object>>> action = switch (group) {
            case "status" -> (g) -> createOkResponse(adminService.getResumeEditCountByStatus());
            case "company" -> (g) -> createOkResponse(adminService.getResumeEditCountByCompany(company));
            case "occupation" -> (g) -> createOkResponse(adminService.getResumeEditCountByOccupation(occupation));
            case "age" -> (g) -> createOkResponse(adminService.getResumeEditCountByAge());
            case "mode" -> (g) -> createOkResponse(adminService.getResumeEditCountByMode());
            case "monthly" -> (g) -> createOkResponse(adminService.getMonthlyResumeEditCount()); // 채용시즌(월별)
            case "weekly" -> (g) -> createOkResponse(adminService.getWeeklyResumeEditCount()); // 채용시즌(주별)
            case "daily" -> (g) -> createOkResponse(adminService.getDailyResumeEditCount()); // 채용시즌(일별)
            default -> (g) -> createBadRequestResponse(Invalid_Request_Error_Message);
        };
        return action.apply(group);
    }

    // 총 첨삭 수
    @GetMapping("/resume/count/total-edit")
    public ResponseEntity<TotalResumeEditCountResponse> getTotalResumeEditCount(){
        return ResponseEntity.ok()
                .body(TotalResumeEditCountResponse.builder()
                        .editTotal(resumeStatisticsService.getTotalResumeEditCount())
                        .build());
    }

    // 오늘 첨삭 수
    @GetMapping("/resume/count/today-edit")
    public ResponseEntity<TodayResumeEditCountResponse> getTodayResumeEditCount(){
        return ResponseEntity.ok()
                .body(TodayResumeEditCountResponse.builder()
                        .editToday(resumeStatisticsService.getTodayResumeEditCount())
                        .build());
    }

    // 총 자소서 게시글 수
    @GetMapping("/resume/count/total-board")
    public ResponseEntity<TotalResumeBoardCountResponse> getTotalResumeBoardCount(){
        return ResponseEntity.ok()
                .body(TotalResumeBoardCountResponse.builder()
                        .boardTotal(resumeStatisticsService.getTotalResumeBoardCount())
                        .build());
    }

    @GetMapping("/resume/monthly")
    public ResponseEntity<Map<String,Object>> getMonthlyResumeEditStatistics() {
        try {
            Map<String, Object> result = new LinkedHashMap<>();

            result.put("edit_monthly", historyService.getMonthlyEditStatistics());

            return createOkResponse(result);
        } catch (Exception e) {
            return createBadRequestResponse(e.getMessage());
        }
    }

    @GetMapping("/resume/weekly")
    public ResponseEntity<Map<String,Object>> getWeeklyResumeEditStatistics(@RequestParam(name="month", required = false) String month) {
        try {
            return createOkResponse(historyService.getWeeklyEditCount(month));
        } catch (Exception e) {
            return createBadRequestResponse(e.getMessage());
        }
    }

    @GetMapping("/resume/daily")
    public ResponseEntity<Map<String,Object>> getDailyResumeEditStatistics(@RequestParam(value = "startDate", required = false) String startDate,
                                                                           @RequestParam(value = "endDate", required = false) String endDate) {
        try {
            Map<String, Object> result = new LinkedHashMap<>();

            Map<String, Object> editDaily = historyService.getDailyEditCount(startDate, endDate);
            result.put("edit_daily", editDaily);
            return createOkResponse(result);
        } catch (Exception e) {
            return createBadRequestResponse(e.getMessage());
        }
    }
}
