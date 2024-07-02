package com.team2.resumeeditorproject.review.controller;

import com.team2.resumeeditorproject.review.domain.Review;
import com.team2.resumeeditorproject.review.dto.ReviewDTO;
import com.team2.resumeeditorproject.review.repository.ReviewRepository;
import com.team2.resumeeditorproject.review.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/review")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private ReviewRepository reviewRepository;

    /* 리뷰 달기 */
    @PostMapping
    public ResponseEntity<Map<String, Object>> setReview(@RequestBody ReviewDTO reviewDTO) {
        Map<String, Object> response = new HashMap<>();
        Date today = new Date();
        try {
            // 리뷰내용 + 별점 받아와서 테이블에 저장
            Review review = reviewRepository.findByUNum(reviewDTO.getUNum());
            if (review != null) { // 이미 리뷰를 작성한 경우면
                throw new Exception(" 리뷰를 이미 작성한 사용자입니다.");
            }

            if (reviewDTO.getContent().length() > 200) { // 댓글 최대 글자수(100)을 넘으면 예외 발생
                throw new Exception("[Failed to write a review] Reviews must not exceed 200 characters");
            }

            reviewService.insertReview(reviewDTO);

            response.put("response", "review table insert success");
            response.put("time", today);
            response.put("status", "Success");

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("response", "server error " + e.getMessage());
            response.put("time", today);
            response.put("status", "Fail");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    /* 리뷰 권한 확인 */
    @GetMapping("/{u_num}")
    public ResponseEntity<Map<String, Object>> setReview(@PathVariable("u_num") Long u_num) {
        Map<String, Object> response = new HashMap<>();
        Date today = new Date();
        try{
            // 리뷰를 이미 작성한 사용자인지 확인 -> 이미 작성했다면 리뷰작성버튼 비활성화
            String result = "true";
            Review review = reviewRepository.findById(u_num).orElse(null);
            if (review != null) { // 이미 리뷰를 작성한 경우면
                result = "false";
            }

            response.put("response", result);
            response.put("time", today);
            response.put("status", "Success");

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("response", "server error " + e.getMessage());
            response.put("time", today);
            response.put("status", "Fail");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
