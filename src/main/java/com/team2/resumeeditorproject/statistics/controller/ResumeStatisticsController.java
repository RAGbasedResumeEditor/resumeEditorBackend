package com.team2.resumeeditorproject.statistics.controller;

import com.team2.resumeeditorproject.admin.dto.request.DailyStatisticsRequest;
import com.team2.resumeeditorproject.admin.dto.request.MonthlyStatisticsRequest;
import com.team2.resumeeditorproject.common.util.DateRange;
import com.team2.resumeeditorproject.common.util.MonthRange;
import com.team2.resumeeditorproject.statistics.dto.response.AgeCountResponse;
import com.team2.resumeeditorproject.statistics.dto.response.DailyResumeEditStatisticsResponse;
import com.team2.resumeeditorproject.statistics.dto.response.ModeCountResponse;
import com.team2.resumeeditorproject.statistics.dto.response.MonthlyResumeEditStatisticsResponse;
import com.team2.resumeeditorproject.statistics.dto.response.StatusCountResponse;
import com.team2.resumeeditorproject.statistics.dto.response.TodayResumeEditCountResponse;
import com.team2.resumeeditorproject.statistics.dto.response.TotalResumeBoardCountResponse;
import com.team2.resumeeditorproject.statistics.dto.response.TotalResumeEditCountResponse;
import com.team2.resumeeditorproject.statistics.service.ResumeStatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/statistics") // /admin 지우기
@RequiredArgsConstructor
public class ResumeStatisticsController {

    private final ResumeStatisticsService resumeStatisticsService;

    @GetMapping("/resume-edit/status")
    public ResponseEntity<StatusCountResponse> getResumeEditCountByStatus(){
        return ResponseEntity.ok()
                .body(StatusCountResponse.builder()
                        .status1(resumeStatisticsService.getResumeEditCountByStatus(1))
                        .status2(resumeStatisticsService.getResumeEditCountByStatus(2))
                        .build());
    }

    @GetMapping("/resume-edit/age")
    public ResponseEntity<AgeCountResponse> getResumeEditCountByAge(){
        return ResponseEntity.ok()
                .body(AgeCountResponse.builder()
                        .count20s(resumeStatisticsService.getResumeEditCountByAgeGroup(20, 29))
                        .count30s(resumeStatisticsService.getResumeEditCountByAgeGroup(30, 39))
                        .count40s(resumeStatisticsService.getResumeEditCountByAgeGroup(40, 49))
                        .count50s(resumeStatisticsService.getResumeEditCountByAgeGroup(50, 59))
                        .count60Plus(resumeStatisticsService.getResumeEditCountByAgeGroup(60, Integer.MAX_VALUE))
                        .build());
    }

    @GetMapping("/resume-edit/mode")
    public ResponseEntity<ModeCountResponse> getResumeEditCountByMode(){
        return ResponseEntity.ok()
                .body(ModeCountResponse.builder()
                        .lightMode(resumeStatisticsService.getResumeEditCountByMode(1))
                        .proMode(resumeStatisticsService.getResumeEditCountByMode(2))
                        .build());
    }

    @GetMapping("/resume-edit/daily")
    public ResponseEntity<DailyResumeEditStatisticsResponse> getDailyResumeEditStatistics(
            @ModelAttribute DailyStatisticsRequest request) {
        DateRange dateRange = request.toDateRange();
        return ResponseEntity.ok()
                .body(DailyResumeEditStatisticsResponse.builder()
                        .editDate(resumeStatisticsService.getDailyResumeEditStatistics(dateRange))
                        .build());
    }

    @GetMapping("/resume-edit/monthly")
    public ResponseEntity<MonthlyResumeEditStatisticsResponse> getDailyResumeEditStatistics(
            @ModelAttribute MonthlyStatisticsRequest request) {
        MonthRange monthRange = request.toMonthRange();
        return ResponseEntity.ok()
                .body(MonthlyResumeEditStatisticsResponse.builder()
                        .editDate(resumeStatisticsService.getMonthlyResumeEditStatistics(monthRange))
                        .build());
    }

    // 총 첨삭 수
    @GetMapping("/resume/count/total-edit")
    public ResponseEntity<TotalResumeEditCountResponse> getTotalResumeEditCount(){
        return ResponseEntity.ok()
                .body(TotalResumeEditCountResponse.builder()
                        .totalEdit(resumeStatisticsService.getTotalResumeEditCount())
                        .build());
    }

    // 오늘 첨삭 수
    @GetMapping("/resume/count/today-edit")
    public ResponseEntity<TodayResumeEditCountResponse> getTodayResumeEditCount(){
        return ResponseEntity.ok()
                .body(TodayResumeEditCountResponse.builder()
                        .todayEdit(resumeStatisticsService.getTodayResumeEditCount())
                        .build());
    }

    // 총 자소서 게시글 수
    @GetMapping("/resume/count/total-board")
    public ResponseEntity<TotalResumeBoardCountResponse> getTotalResumeBoardCount(){
        return ResponseEntity.ok()
                .body(TotalResumeBoardCountResponse.builder()
                        .totalBoard(resumeStatisticsService.getTotalResumeBoardCount())
                        .build());
    }
}
