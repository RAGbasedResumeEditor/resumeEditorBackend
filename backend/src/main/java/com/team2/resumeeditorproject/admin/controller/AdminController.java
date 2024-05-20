package com.team2.resumeeditorproject.admin.controller;

import com.team2.resumeeditorproject.admin.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;
import java.util.function.Function;

import static com.team2.resumeeditorproject.admin.service.ResponseHandler.*;

@Controller
@RequestMapping("/admin/stat")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @GetMapping("/user")
    public ResponseEntity<Map<String,Object>> getUserCnt(@RequestParam(name="group", required=false) String group,
                                                         @RequestParam(name="occupation", required = false) String occupation,
                                                         @RequestParam(name="wish", required = false) String wish){
        try {
            Function<String, ResponseEntity<Map<String, Object>>> action = switch (group) {
                case "count" -> (g) -> createResponse(adminService.userCnt());
                case "gender" -> (g) -> createResponse(adminService.genderCnt());
                case "age" -> (g) -> createResponse(adminService.ageCnt());
                case "status" -> (g) -> createResponse(adminService.statusCnt());
                case "mode" -> (g) -> createResponse(adminService.modeCnt());
                case "occupation" -> (g) -> createResponse(adminService.occupCnt(occupation));
                case "wish" -> (g) -> createResponse(adminService.wishCnt(wish));
                default -> (g) ->  createBadReqResponse("잘못된 요청입니다.");
            };
            return action.apply(group);
        }catch(Exception e){
            return createServerErrResponse();
        }
    }

    @GetMapping("/board")
    public ResponseEntity<Map<String,Object>> getResumeStatByCompany(@RequestParam(name="group", defaultValue = "company") String group,
                                                                     @RequestParam(name="company", required = false) String company,
                                                                     @RequestParam(name="occupation", required=false) String occupation){
        try {
            Function<String, ResponseEntity<Map<String, Object>>> action = switch (group) {
                case "company" -> (g) -> createResponse(adminService.CompResumeCnt(company));
                case "occupation" -> (g) -> createResponse(adminService.OccupResumeCnt(occupation));
                default -> (g) ->  createBadReqResponse("잘못된 요청입니다.");
            };
            return action.apply(group);
        }catch(Exception e){
            return createServerErrResponse();
        }
    }

    /* 신입/경력 별 첨삭 횟수 */
    @GetMapping("/resumeEdit/status")
    public ResponseEntity<Map<String, Object>> getResumeEditCountByStatus() {
        try{
            return createResponse(adminService.resumeEditCntByStatus());
        }catch(Exception e){
            return createBadReqResponse("잘못된 요청입니다");
        }
    }
}
