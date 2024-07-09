package com.team2.resumeeditorproject.admin.controller;

import com.team2.resumeeditorproject.admin.dto.response.TotalResumeBoardCountResponse;
import com.team2.resumeeditorproject.admin.dto.response.TotalResumeEditCountResponse;
import com.team2.resumeeditorproject.admin.dto.response.UserCountResponse;
import com.team2.resumeeditorproject.admin.dto.response.VisitTotalCountResponse;
import com.team2.resumeeditorproject.admin.service.AdminService;
import com.team2.resumeeditorproject.admin.service.HistoryService;
import com.team2.resumeeditorproject.admin.service.ResumeStatisticsService;
import com.team2.resumeeditorproject.admin.service.ReviewManagementService;
import com.team2.resumeeditorproject.admin.service.UserStatisticsService;
import com.team2.resumeeditorproject.review.domain.Review;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.team2.resumeeditorproject.common.util.ResponseHandler.createBadRequestResponse;
import static com.team2.resumeeditorproject.common.util.ResponseHandler.createOkResponse;

@Controller
@RequestMapping("/landing")
@RequiredArgsConstructor
public class LandingController {

    private final AdminService adminService;
    private final HistoryService historyService;
    private final ReviewManagementService reviewService;

    private final UserStatisticsService userStatisticsService;
    private final ResumeStatisticsService resumeStatisticsService;

    @GetMapping("/statistics/user-count")
    public ResponseEntity<UserCountResponse> getUserCount() {
        return ResponseEntity.ok()
                .body(UserCountResponse.builder()
                        .totalCount(userStatisticsService.getUserCount())
                        .build());
    }

    @GetMapping("/statistics/total-visit")
    public ResponseEntity<VisitTotalCountResponse> getTotalVisitCount() {
        return ResponseEntity.ok()
                .body(VisitTotalCountResponse.builder()
                        .visitTotal(userStatisticsService.getTotalVisitCount())
                        .build());
    }

    @GetMapping("/statistics/total-edit")
    public ResponseEntity<TotalResumeEditCountResponse> getTotalResumeEditCount(){
        return ResponseEntity.ok()
                .body(TotalResumeEditCountResponse.builder()
                        .totalEdit(resumeStatisticsService.getTotalResumeEditCount())
                        .build());
    }

    @GetMapping("/statistics/total-board")
    public ResponseEntity<TotalResumeBoardCountResponse> getTotalResumeBoardCount(){
        return ResponseEntity.ok()
                .body(TotalResumeBoardCountResponse.builder()
                        .totalBoard(resumeStatisticsService.getTotalResumeBoardCount())
                        .build());
    }

    @GetMapping("/review")
    public ResponseEntity<Map<String,Object>> getAllVisibleReviews() {
        try {
            List<Review> reviews = reviewService.getVisibleReviews();

            Map<String, Object> response = new HashMap<>();
            response.put("review", reviews);

            return createOkResponse(response);
        } catch (Exception e) {
            return createBadRequestResponse(e.getMessage());
        }

    }
}
