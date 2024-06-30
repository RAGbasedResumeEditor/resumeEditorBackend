package com.team2.resumeeditorproject.admin.controller;

import com.team2.resumeeditorproject.admin.service.AdminService;
import com.team2.resumeeditorproject.admin.service.HistoryService;
import com.team2.resumeeditorproject.admin.service.ReviewManagementService;
import com.team2.resumeeditorproject.review.domain.Review;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static com.team2.resumeeditorproject.admin.service.ResponseHandler.createBadReqResponse;
import static com.team2.resumeeditorproject.admin.service.ResponseHandler.createResponse;

@Controller
@RequestMapping("/landing")
@RequiredArgsConstructor
public class LandingController {

    private final AdminService adminService;
    private final HistoryService historyService;
    private final ReviewManagementService reviewService;

    @GetMapping("/stat")
    public ResponseEntity<Map<String,Object>> getStatistics(@RequestParam(name="group", required=false) String group){
        Function<String, ResponseEntity<Map<String, Object>>> action = switch (group) {
            case "countUser" -> (g) -> createResponse(adminService.userCnt());
            case "visitTotal" -> (g) -> createResponse(historyService.getTotalTraffic());
            case "editTotal" -> (g) -> createResponse(historyService.getTotalEdit());
            case "boardTotal" -> (g) -> createResponse(historyService.getTotalBoardCnt());
            default -> (g) ->  createBadReqResponse("잘못된 요청입니다.");
        };
        return action.apply(group);
    }

    @GetMapping("/review")
    public ResponseEntity<Map<String,Object>> getAllVisibleReviews() {
        try {
            List<Review> reviews = reviewService.getVisibleReviews();

            Map<String, Object> response = new HashMap<>();
            response.put("review", reviews);

            return createResponse(response);
        }catch(Exception e){
            return createBadReqResponse(e.getMessage());
        }

    }
}
