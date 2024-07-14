package com.team2.resumeeditorproject.admin.controller;

import com.team2.resumeeditorproject.admin.dto.response.ReviewListResponse;
import com.team2.resumeeditorproject.admin.service.ReviewManagementService;
import com.team2.resumeeditorproject.common.util.CommonResponse;
import com.team2.resumeeditorproject.review.dto.ReviewDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;

@Controller
@RequestMapping("/admin/review")
@RequiredArgsConstructor
public class ReviewManagementController {

    private final ReviewManagementService reviewService;

    private static final int SIZE_OF_PAGE = 10; // 한 페이지에 보여줄 회원 수

    @GetMapping("/list")
    public ResponseEntity<ReviewListResponse> getAllReviews(
            @RequestParam(defaultValue = "0", name = "pageNo") int pageNo) {

        Page<ReviewDTO> reviewPage = reviewService.getPagedReviews(pageNo, SIZE_OF_PAGE);

        return ResponseEntity.ok()
                .body(ReviewListResponse.builder()
                        .totalPage(reviewPage.getTotalPages())
                        .reviewDTOList(reviewPage.getContent())
                        .build());
    }

    @GetMapping("/list/display-review")
    public ResponseEntity<ReviewListResponse> getShowReviews(@RequestParam("pageNo") int pageNo) {

        Page<ReviewDTO> reviewPage = reviewService.getDisplayReviews(pageNo, SIZE_OF_PAGE);

        return ResponseEntity.ok()
                .body(ReviewListResponse.builder()
                        .totalPage(reviewPage.getTotalPages())
                        .reviewDTOList(reviewPage.getContent())
                        .build());
    }

    @PostMapping("/display")
    public ResponseEntity<CommonResponse> selectDisplayReview(@RequestParam("reviewNo") Long reviewNo) {
        return ResponseEntity.ok()
                .body(CommonResponse.builder()
                        .response(reviewService.selectReview(reviewNo))
                        .status("Success")
                        .time(new Date())
                        .build());
    }
}
