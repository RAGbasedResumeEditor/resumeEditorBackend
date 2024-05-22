package com.team2.resumeeditorproject.admin.controller;

import com.team2.resumeeditorproject.admin.service.AdminService;
import com.team2.resumeeditorproject.user.dto.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import static com.team2.resumeeditorproject.admin.service.ResponseHandler.*;

@Controller
@RequestMapping("/admin/stat")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    /* 유저 정보에 관한 통계 */
    @GetMapping("/user")
    public ResponseEntity<Map<String,Object>> getUserCnt(@RequestParam(name="group", defaultValue = "occupation") String group,
                                                         @RequestParam(name="occupation", required = false) String occupation,
                                                         @RequestParam(name="wish", required = false) String wish){
        try {
            if(group.equals("count")){
                return createResponse(adminService.userCnt());
            }else if(group.equals("gender")){
                return createResponse(adminService.genderCnt());
            }else if(group.equals("age")){
                return createResponse(adminService.ageCnt());
            }else if(group.equals("status")){
                return createResponse(adminService.statusCnt());
            }else if(group.equals("mode")){
                return createResponse(adminService.modeCnt());
            }else if(group.equals("occupation")){
                return createResponse(adminService.occupCnt(occupation));
            }else if(group.equals("wish")){
                return createResponse(adminService.wishCnt(wish));
            } else{
                return createBadReqResponse("잘못된 요청입니다.");
            }
        }catch(Exception e){
            return createServerErrResponse();
        }
    }

    /* 자소서 목록에 관한 통계 */
    @GetMapping("/board")
    public ResponseEntity<Map<String,Object>> getResumeStatByCompany(@RequestParam(name="group", defaultValue = "company") String group,
                                                                     @RequestParam(name="company", required = false) String company,
                                                                     @RequestParam(name="occupation", required=false) String occupation){
        try {
            if(group.equals("company")){
                return createResponse(adminService.CompResumeCnt(company));
            }else if(group.equals("occupation")){
                return createResponse(adminService.OccupResumeCnt(occupation));
            }else{
                return createBadReqResponse("잘못된 요청입니다.");
            }
        }catch(Exception e){
            return createServerErrResponse();
        }
    }

    /* 자소서 첨삭 이용에 관한 통계 */
    @GetMapping("/resumeEdit")
    public ResponseEntity<Map<String, Object>> getResumeEditCountByStatus(@RequestParam(name="group", required = false) String group,
                                                                          @RequestParam(name="company", required = false) String company,
                                                                          @RequestParam(name="occupation", required = false) String occupation) {
        try {
            Function<String, ResponseEntity<Map<String, Object>>> action = switch (group) {
                case "status" -> (g) -> createResponse(adminService.resumeEditCntByStatus());
                case "company" -> (g) -> createResponse(adminService.resumeEditCntByComp(company));
                case "occupation" -> (g) -> createResponse(adminService.resumeEditCntByOccup(occupation));
                case "age" -> (g) -> createResponse(adminService.resumeEditCntByAge());
                case "mode" -> (g) -> createResponse(adminService.resumeEditCntByMode());
                case "monthly" -> (g) -> createResponse(adminService.resumeCntByMonth()); // 채용시즌(월별)
                case "weekly" -> (g) -> createResponse(adminService.resumeCntByWeekly()); // 채용시즌(주별)
                case "daily" -> (g) -> createResponse(adminService.resumeCntByDaily()); // 채용시즌(일별)
                default -> (g) -> createBadReqResponse("잘못된 요청입니다");
            };

            return action.apply(group);
        } catch (Exception e) {
            return createServerErrResponse();
        }
    }
}
