package com.team2.resumeeditorproject.admin.controller;

import com.team2.resumeeditorproject.admin.dto.LandingPageReviewDTO;
import com.team2.resumeeditorproject.admin.dto.response.LandingPageReviewsResponse;
import com.team2.resumeeditorproject.admin.service.ReviewManagementService;
import com.team2.resumeeditorproject.statistics.dto.response.TotalResumeBoardCountResponse;
import com.team2.resumeeditorproject.statistics.dto.response.TotalResumeEditCountResponse;
import com.team2.resumeeditorproject.statistics.dto.response.UserCountResponse;
import com.team2.resumeeditorproject.statistics.dto.response.VisitTotalCountResponse;
import com.team2.resumeeditorproject.statistics.service.ResumeStatisticsService;
import com.team2.resumeeditorproject.statistics.service.UserStatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/landing")
@RequiredArgsConstructor
public class LandingController {

    private final ReviewManagementService reviewService;
    private final UserStatisticsService userStatisticsService;
    private final ResumeStatisticsService resumeStatisticsService;
    private final ReviewManagementService reviewManagementService;

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
    public ResponseEntity<LandingPageReviewsResponse> getAllVisibleReviews() {
        List<LandingPageReviewDTO> reviewDTOs = reviewManagementService.getVisibleReviews();

        return ResponseEntity.ok()
                .body(LandingPageReviewsResponse.builder()
                        .reviews(reviewDTOs)
                        .build());
    }
}
