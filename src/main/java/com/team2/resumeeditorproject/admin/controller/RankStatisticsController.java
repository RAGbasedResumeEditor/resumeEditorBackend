package com.team2.resumeeditorproject.admin.controller;

import com.team2.resumeeditorproject.admin.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

import static com.team2.resumeeditorproject.admin.service.ResponseHandler.createOkResponse;

@Controller
@RequestMapping("/admin/statistics") // /admin 지우기
@RequiredArgsConstructor
public class RankStatisticsController {

    private final AdminService adminService;

    // 관리자 - 통계 페이지 내에서 각 영역 top 5를 출력한다.
    @GetMapping("/rank/occupation")
    public ResponseEntity<Map<String, Object>> getOccupationRank() {
        return createOkResponse(adminService.getOccupationRank());
    }

    @GetMapping("/rank/company")
    public ResponseEntity<Map<String, Object>> getCompanyRank() {
        return createOkResponse(adminService.getCompanyRank());
    }

    @GetMapping("/rank/wish")
    public ResponseEntity<Map<String, Object>> getWishRank() {
        return createOkResponse(adminService.getWishRank());
    }


}
