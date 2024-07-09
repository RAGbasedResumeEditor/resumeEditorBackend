package com.team2.resumeeditorproject.admin.controller;

import com.team2.resumeeditorproject.admin.dto.response.RankByResumeEditResponse;
import com.team2.resumeeditorproject.admin.dto.response.RankByUserResponse;
import com.team2.resumeeditorproject.admin.service.RankStatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/statistics") // /admin 지우기
@RequiredArgsConstructor
public class RankStatisticsController {

    private final RankStatisticsService rankStatisticsService;

    @GetMapping("/user/rank/occupation")
    public ResponseEntity<RankByUserResponse> getTopOccupationRanksByUsers() {
        return ResponseEntity.ok()
                .body(RankByUserResponse.builder()
                        .userRanking(rankStatisticsService.getTopOccupationRanksByUsers())
                        .build());
    }

    @GetMapping("/user/rank/company")
    public ResponseEntity<RankByUserResponse> getTopCompanyRanksByUsers() {
        return ResponseEntity.ok()
                .body(RankByUserResponse.builder()
                        .userRanking(rankStatisticsService.getTopCompanyRanksByUsers())
                        .build());
    }

    @GetMapping("/user/rank/wish")
    public ResponseEntity<RankByUserResponse> getTopWishRanksByUsers() {
        return ResponseEntity.ok()
                .body(RankByUserResponse.builder()
                        .userRanking(rankStatisticsService.getTopWishRanksByUsers())
                        .build());
    }

    @GetMapping("/resume-edit/rank/occupation")
    public ResponseEntity<RankByResumeEditResponse> getTopOccupationRanksByResumeEdits() {
        return ResponseEntity.ok()
                .body(RankByResumeEditResponse.builder()
                        .editRanking(rankStatisticsService.getTopOccupationRanksByResumeEdits())
                        .build());
    }

    @GetMapping("/resume-edit/rank/company")
    public ResponseEntity<RankByResumeEditResponse> getTopCompanyRanksByResumeEdits() {
        return ResponseEntity.ok()
                .body(RankByResumeEditResponse.builder()
                        .editRanking(rankStatisticsService.getTopCompanyRanksByResumeEdits())
                        .build());
    }
}
