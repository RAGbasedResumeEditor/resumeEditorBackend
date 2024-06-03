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

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import java.util.ArrayList;
import java.util.List;

import static com.team2.resumeeditorproject.admin.service.ResponseHandler.createPagedResponse;


@Controller
@RequestMapping("/admin/review")
@RequiredArgsConstructor
public class ReviewManagementController {

    private final ReviewManagementService reviewService;

    @GetMapping("/list")
    public ResponseEntity<Map<String,Object>> getAllReviews(@RequestParam("page") int page){
        if(page<0){
            page=0;
        }

        Page<Review> rvList=reviewService.getAllReviews(page);
        int totalPage=rvList.getTotalPages();

        List<ReviewDTO> rvDtoList = new ArrayList<>();
        for (Review rv : rvList) {
            ReviewDTO rvDto = new ReviewDTO();
            rvDto.setRv_num(rv.getRvNum());
            rvDto.setU_num(rv.getUNum());
            rvDto.setContent(rv.getContent());
            rvDto.setRating(rv.getRating());
            rvDto.setMode(rv.getMode());
            rvDto.setW_date(rv.getW_date());
            rvDto.setShow(rv.isShow());
            rvDtoList.add(rvDto);
        }

        if(rvDtoList.isEmpty()){
            throw new BadRequestException("후기가 존재하지 않습니다.");
        }

        return createPagedResponse(totalPage,rvDtoList);
    }

    @GetMapping("/list/show")
    public ResponseEntity<Map<String, Object>> getShowReviews(@RequestParam("page") int page){

            if(page<0){
                page=0;
            }

            Page<Review> rvList =reviewService.getAllShows(page);
            int totalPage=rvList.getTotalPages();

            List<ReviewDTO> rvDtoList = new ArrayList<>();
            for (Review rv : rvList) {
                ReviewDTO rvDto = new ReviewDTO();
                rvDto.setRv_num(rv.getRvNum());
                rvDto.setU_num(rv.getUNum());
                rvDto.setContent(rv.getContent());
                rvDto.setRating(rv.getRating());
                rvDto.setMode(rv.getMode());
                rvDto.setW_date(rv.getW_date());
                rvDto.setShow(rv.isShow());
                rvDtoList.add(rvDto);
            }

            if(rvDtoList.isEmpty()){
                throw new BadRequestException("후기가 존재하지 않습니다.");
            }

        return createPagedResponse(totalPage,rvDtoList);
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
