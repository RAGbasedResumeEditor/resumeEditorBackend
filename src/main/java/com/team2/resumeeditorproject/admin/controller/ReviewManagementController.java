package com.team2.resumeeditorproject.admin.controller;

import com.team2.resumeeditorproject.admin.dto.response.ReviewListResponse;
import com.team2.resumeeditorproject.admin.service.ReviewManagementService;
import com.team2.resumeeditorproject.common.util.CommonResponse;
import com.team2.resumeeditorproject.exception.NotFoundException;
import com.team2.resumeeditorproject.review.domain.Review;
import com.team2.resumeeditorproject.review.dto.ReviewDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin/review")
@RequiredArgsConstructor
public class ReviewManagementController {

    private final ReviewManagementService reviewService;

    private static final int SIZE_OF_PAGE = 10; // 한 페이지에 보여줄 회원 수

    @GetMapping("/list")
    public ResponseEntity<ReviewListResponse> getAllReviews(
            @RequestParam(defaultValue = "0", name = "pageNo") int pageNo) {

        int size = SIZE_OF_PAGE;

        if (pageNo < 0) {
            pageNo = 0;
        }

        Page<Review> reviewList = reviewService.getPagedReviews(pageNo);

        List<ReviewDTO> reviewDTOList = new ArrayList<>();
        for (Review review : reviewList) {
            ReviewDTO reviewDTO = new ReviewDTO();
            reviewDTO.setRvNum(review.getRvNum());
            reviewDTO.setUNum(review.getUNum());
            reviewDTO.setContent(review.getContent());
            reviewDTO.setRating(review.getRating());
            reviewDTO.setMode(review.getMode());
            reviewDTO.setW_date(review.getW_date());
            reviewDTO.setDisplay(review.getDisplay());
            reviewDTOList.add(reviewDTO);
        }

        if (reviewDTOList.isEmpty()) {
            throw new NotFoundException("후기가 존재하지 않습니다.");
        }

        return ResponseEntity.ok()
                .body(ReviewListResponse.builder()
                .totalPage(reviewList.getTotalPages())
                .reviewDTOList(reviewDTOList)
                        .build());
    }

    @GetMapping("/list/display-review")
    public ResponseEntity<ReviewListResponse> getShowReviews(@RequestParam("pageNo") int pageNo) {
        if (pageNo < 0) {
            pageNo = 0;
        }

        Page<Review> reviewList = reviewService.getDisplayReviews(pageNo);

        List<ReviewDTO> reviewDTOList = reviewList.stream()
                .map(review -> new ReviewDTO(
                        review.getRvNum(),
                        review.getUNum(),
                        review.getContent(),
                        review.getRating(),
                        review.getMode(),
                        review.getDisplay(),
                        review.getW_date()
                ))
                .collect(Collectors.toList());
        if (reviewDTOList.isEmpty()) {
            throw new NotFoundException("후기가 존재하지 않습니다.");
        }

        return ResponseEntity.ok()
                .body(ReviewListResponse.builder()
                        .totalPage(reviewList.getTotalPages())
                        .reviewDTOList(reviewDTOList)
                        .build());
    }

    @PostMapping("/display")
    public ResponseEntity<CommonResponse> selectDisplayReview(@RequestParam("reviewNo") Long reviewNo) {
        try {
            return ResponseEntity.ok()
                    .body(CommonResponse.builder()
                            .response(reviewService.selectReview(reviewNo))
                            .status("Success")
                            .time(new Date())
                            .build());
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(CommonResponse.builder()
                            .response("Failed to selected")
                            .status("Fail")
                            .time(new Date())
                            .build());
        }
    }
}
