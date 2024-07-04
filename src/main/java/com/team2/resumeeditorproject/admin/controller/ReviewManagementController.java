package com.team2.resumeeditorproject.admin.controller;

import com.team2.resumeeditorproject.admin.dto.ReviewDTO;
import com.team2.resumeeditorproject.admin.service.ReviewManagementService;
import com.team2.resumeeditorproject.exception.BadRequestException;
import com.team2.resumeeditorproject.review.domain.Review;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.team2.resumeeditorproject.common.util.ResponseHandler.createPagedResponse;

@Controller
@RequestMapping("/admin/review")
@RequiredArgsConstructor
public class ReviewManagementController {

    private final ReviewManagementService reviewService;

    @GetMapping("/list")
    public ResponseEntity<Map<String,Object>> getAllReviews(@RequestParam("pageNo") int pageNo) {
        if (pageNo < 0) {
            pageNo = 0;
        }

        Page<Review> reviewList = reviewService.getPagedReviews(pageNo);
        int totalPage=reviewList.getTotalPages();

        List<ReviewDTO> reviewDTOList = new ArrayList<>();
        for (Review review : reviewList) {
            ReviewDTO reviewDTO = new ReviewDTO();
            reviewDTO.setRv_num(review.getRvNum());
            reviewDTO.setU_num(review.getUNum());
            reviewDTO.setContent(review.getContent());
            reviewDTO.setRating(review.getRating());
            reviewDTO.setMode(review.getMode());
            reviewDTO.setW_date(review.getW_date());
            reviewDTO.setShow(review.isShow());
            reviewDTOList.add(reviewDTO);
        }

        if (reviewDTOList.isEmpty()) {
            throw new BadRequestException("후기가 존재하지 않습니다.");
        }

        return createPagedResponse(totalPage, reviewDTOList);
    }

    @GetMapping("/list/show")
    public ResponseEntity<Map<String, Object>> getShowReviews(@RequestParam("pageNo") int pageNo) {
            if (pageNo < 0) {
                pageNo = 0;
            }

            Page<Review> reviewList = reviewService.getAllShows(pageNo);
            int totalPage = reviewList.getTotalPages();

            List<ReviewDTO> reviewDTOList = new ArrayList<>();
            for (Review review : reviewList) {
                ReviewDTO reviewDTO = new ReviewDTO();
                reviewDTO.setRv_num(review.getRvNum());
                reviewDTO.setU_num(review.getUNum());
                reviewDTO.setContent(review.getContent());
                reviewDTO.setRating(review.getRating());
                reviewDTO.setMode(review.getMode());
                reviewDTO.setW_date(review.getW_date());
                reviewDTO.setShow(review.isShow());
                reviewDTOList.add(reviewDTO);
            }

            if (reviewDTOList.isEmpty()) {
                throw new BadRequestException("후기가 존재하지 않습니다.");
            }
        return createPagedResponse(totalPage,reviewDTOList);
    }

    @PostMapping("/select")
    public ResponseEntity<Map<String, Object>> selectReview(@RequestParam("rvNum") Long rvNum) {
        try {
            Map<String, Object> response = new HashMap<>();
            if (reviewService.selectReview(rvNum)) {
                response.put("response", "Review selected successfully");
                response.put("status", "Success");
            } else {
                response.put("response", "Already selected");
                response.put("status", "Fail");
            }
            response.put("time", new Date());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("response", "Failed to selected : " + e.getMessage());
            errorResponse.put("time", new Date());
            errorResponse.put("status", "Fail");
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }
}
