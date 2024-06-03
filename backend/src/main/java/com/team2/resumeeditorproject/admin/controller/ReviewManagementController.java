package com.team2.resumeeditorproject.admin.controller;

import com.team2.resumeeditorproject.admin.service.ReviewManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@Controller
@RequestMapping("/admin/review")
@RequiredArgsConstructor
public class ReviewManagementController {

    @Autowired
    private ReviewManagementService reviewService;

    @PostMapping("/select")
    public ResponseEntity<Map<String, Object>> selectReview(@RequestParam("rvNum") Long rvNum) {
        try {
            Map<String, Object> response = new HashMap<>();
            if (reviewService.selectReview(rvNum)) {
                response.put("response", "Review selected successfully");
                response.put("status", "success");
            } else {
                response.put("response", "Already selected");
                response.put("status", "fail");
            }
            response.put("time", new Date());
            return ResponseEntity.ok(response);
        }catch(Exception e){
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("response", "Failed to selected : "+ e.getMessage());
            errorResponse.put("time", new Date());
            errorResponse.put("status", "Fail");
            return ResponseEntity.badRequest().body(errorResponse);
        }

    }
}
