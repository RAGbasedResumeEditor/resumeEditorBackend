package com.team2.resumeeditorproject.admin.controller;

import com.team2.resumeeditorproject.admin.service.ReviewManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/admin/review")
@RequiredArgsConstructor
public class ReviewManagementController {

    @Autowired
    private ReviewManagementService reviewService;

    @PostMapping("/select")
    public ResponseEntity<?> selectReview(@RequestParam("rvNum") Long rvNum) {
        reviewService.selectReview(rvNum);
        return ResponseEntity.ok("Review selected successfully");
    }
}
