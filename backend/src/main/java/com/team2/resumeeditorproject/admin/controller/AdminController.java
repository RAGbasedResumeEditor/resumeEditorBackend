package com.team2.resumeeditorproject.admin.controller;

import com.team2.resumeeditorproject.admin.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

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
        try{
            if(group.equals("status")){
                return createResponse(adminService.resumeEditCntByStatus());
            }else if(group.equals("company")){
                return createResponse(adminService.resumeEditCntByComp(company));
            }else if(group.equals("occupation")){
                return createResponse(adminService.resumeEditCntByOccup(occupation));
            }else if(group.equals("age")){
                return createResponse(adminService.resumeEditCntByAge());
            }else if(group.equals("mode")){
                return createResponse(adminService.resumeEditCntByMode());
            }else if(group.equals("month")){ // 채용시즌(월별)
                return createResponse(adminService.resumeCntByMonth());
            }else if(group.equals("weekly")){ // 채용시즌(주별)
                return createResponse(adminService.resumeCntByWeekly());
            }else if(group.equals("daily")){ // 채용시즌(일별)
                return createResponse(adminService.resumeCntByDaily());
            }
            else{
                return createBadReqResponse("잘못된 요청입니다");
            }

        }catch(Exception e){
            return createServerErrResponse();
        }
    }
}
