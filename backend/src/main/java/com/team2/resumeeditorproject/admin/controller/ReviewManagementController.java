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

// TODO : import할 때 와일드카드는 사용하지 않는 것이 좋음
import java.util.*;

import static com.team2.resumeeditorproject.admin.service.ResponseHandler.createPagedResponse;


@Controller
@RequestMapping("/admin/review")
@RequiredArgsConstructor
public class ReviewManagementController {

    private final ReviewManagementService reviewService;

    @GetMapping("/list")
    public ResponseEntity<Map<String,Object>> getAllReviews(@RequestParam("page") int page){
        // TODO : page가 0보다 작을 때 0으로 보정하는 기능이 자주사용되는데 util 클래스로 빼는 것이 좋아보임
        if(page<0){
            page=0;
        }

        // TODO : 페이징된 결과를 가져오는데 get all reviews는 맞지 않아보임
        // TODO : = 사이에 공백 필요
        Page<Review> rvList=reviewService.getAllReviews(page);
        int totalPage=rvList.getTotalPages();

        List<ReviewDTO> rvDtoList = new ArrayList<>(); // TODO : rvList를 stream으로 rvDtoList를 만들어도 되는데 stream이 어렵다면 건너뛰어도 됨
        for (Review rv : rvList) {
            ReviewDTO rvDto = new ReviewDTO();
            // TODO : rvnum, unnum 같은 명칭은 변경할 필요가 있어보임, u나 rv에 해당하는 신규테이블이 추가될 때를 고려해야 설계일관성 자체가 깨지기 쉬움
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

    //
    @GetMapping("/list/show")
    public ResponseEntity<Map<String, Object>> getShowReviews(@RequestParam("page") int page){

            if(page<0){
                page=0;
            }

            // TODO : review의 show 속성이 true인 것만 가져오는 것 같은데 get all shows 라는 이름은 잘못된듯, url도 마찬가지
            // TODO : 당장 추가할 수 있는건 아니겠지만 권한관리도 필요해보임
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

    // TODO : select라는 이름이 query에서 select라는 이름과 혼동되기 쉬워서 다른 이름을 붙이는게 좋아보임
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
            
            // TODO : api에 time과 같은 응답을 전달하려면 프로젝트 전반에 일관성이 있는게 좋아보임
            response.put("time", new Date());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            // TODO : api 응답으로 Exception의 메시지를 직접전달하는 것은 좋은 방법이 아님, 커스텀 exception이라면 용도에 따라서 전달할 수도 있지만 여기는 catch로 Exception을 잡고있기 때문에...
            errorResponse.put("response", "Failed to selected : " + e.getMessage());
            errorResponse.put("time", new Date());
            errorResponse.put("status", "Fail");
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }
}
