package com.team2.resumeeditorproject.admin.controller;

import com.team2.resumeeditorproject.admin.domain.Review;
import com.team2.resumeeditorproject.admin.dto.ReviewDTO;
import com.team2.resumeeditorproject.admin.service.ReviewManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.team2.resumeeditorproject.admin.service.ResponseHandler.createPagedResponse;


@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class ReviewManagementController {

    private final ReviewManagementService reviewManagementService;

    @GetMapping("/review/show")
    public ResponseEntity<Map<String, Object>> getShowReviews(@RequestParam("page") int page){
            Page<Review> rvList =reviewManagementService.getAllShows(page);
            int totalPage=rvList.getTotalPages();

        List<ReviewDTO> rvDtoList = new ArrayList<>();
        for (Review rv : rvList) {
            ReviewDTO rvDto = new ReviewDTO();
            rvDto.setRv_num(rv.getRv_num());
            rvDto.setU_num(rv.getU_num());
            rvDto.setContent(rv.getContent());
            rvDto.setRating(rv.getRating());
            rvDto.setMode(rv.getMode());
            rvDto.setW_date(rv.getW_date());
            rvDtoList.add(rvDto);
        }

        return createPagedResponse(totalPage,rvDtoList);
    }
}
