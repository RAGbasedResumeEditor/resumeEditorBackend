package com.team2.resumeeditorproject.admin.controller;

import com.team2.resumeeditorproject.admin.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/admin/stat")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @GetMapping("/user")
    public ResponseEntity<Map<String,Object>> getUserCnt(){
        try {
            Map<String,Object> response=adminService.userCnt();
            return ResponseEntity.ok().body(response);
        }catch(Exception e){
            Map<String,Object> errorResponse=new HashMap<>();
            errorResponse.put("status","Fail");
            errorResponse.put("time",new Date());
            errorResponse.put("response", "서버 오류입니다.");
            return ResponseEntity.internalServerError().body(errorResponse);
        }
    }

    @GetMapping("/user/gender")
    public ResponseEntity<Map<String,Object>> getUserGender(){
        try {
            Map<String, Object> response=adminService.genderCnt();
            return ResponseEntity.ok().body(response);
        }catch(Exception e){
            Map<String, Object> errorResponse=new HashMap<>();
            errorResponse.put("status","Fail");
            errorResponse.put("time",new Date());
            errorResponse.put("response", "서버 오류입니다.");
            return ResponseEntity.internalServerError().body(errorResponse);
        }
    }

    @GetMapping("/user/occupation/{occupation}")
    public ResponseEntity<Map<String,Object>> getUserOccupation(@PathVariable("occupation") String occupation){
        try {
            Map<String,Object> response=adminService.occupCnt(occupation);
            return ResponseEntity.ok().body(response);
        }catch(Exception e){
            Map<String,Object> errorResponse=new HashMap<>();
            errorResponse.put("status","Fail");
            errorResponse.put("time",new Date());
            errorResponse.put("response", "서버 오류입니다.");
            return ResponseEntity.internalServerError().body(errorResponse);
        }
    }

    @GetMapping("/user/age")
    public ResponseEntity<Map<String,Object>> getUserAge(){
        try {
            Map<String,Object> response = new HashMap<>();
            Map<String, Integer> ageCnt=adminService.ageCnt();
            response.put("response", ageCnt);
            return ResponseEntity.ok().body(response);
        }catch(Exception e){
            Map<String,Object> errorResponse=new HashMap<>();
            errorResponse.put("status","Fail");
            errorResponse.put("time",new Date());
            errorResponse.put("response", "서버 오류입니다.");
            return ResponseEntity.internalServerError().body(errorResponse);
        }
    }

    @GetMapping("/user/wish/{wish}")
    public ResponseEntity<Map<String,Object>> getUserWish(@RequestParam("wish") String wish){

        try {
            Map<String,Object> response=adminService.wishCnt(wish);
            return ResponseEntity.ok().body(response);
        }catch(Exception e){
            Map<String,Object> errorResponse=new HashMap<>();
            errorResponse.put("status","Fail");
            errorResponse.put("time",new Date());
            errorResponse.put("response", "서버 오류입니다.");
            return ResponseEntity.internalServerError().body(errorResponse);
        }
    }

    @GetMapping("/user/status")
    public ResponseEntity<Map<String,Object>> getUserStatus(){
        try {
            Map<String,Object> response=adminService.statusCnt();
            return ResponseEntity.ok().body(response);
        }catch(Exception e){
            Map<String,Object> errorResponse=new HashMap<>();
            errorResponse.put("status","Fail");
            errorResponse.put("time",new Date());
            errorResponse.put("response", "서버 오류입니다.");
            return ResponseEntity.internalServerError().body(errorResponse);
        }
    }

    @GetMapping("/user/mode")
    public ResponseEntity<Map<String,Object>> getUserMode(){
        try {
            Map<String, Object> modeCnt=adminService.modeCnt();
            return ResponseEntity.ok().body(modeCnt);
        }catch(Exception e){
            Map<String,Object> errorResponse=new HashMap<>();
            errorResponse.put("status","Fail");
            errorResponse.put("time",new Date());
            errorResponse.put("response", "서버 오류입니다.");
            return ResponseEntity.internalServerError().body(errorResponse);
        }
    }

    @GetMapping("/resume/company/{company}")
    public ResponseEntity<Map<String,Object>> getResumeStatByCompany(@RequestParam("company") String company){
        try {
            Map<String, Object> response=adminService.CompResumeCnt(company);
            return ResponseEntity.ok().body(response);
        }catch(Exception e){
            Map<String,Object> errorResponse=new HashMap<>();
            errorResponse.put("status","Fail");
            errorResponse.put("time",new Date());
            errorResponse.put("response", "서버 오류입니다.");
            return ResponseEntity.internalServerError().body(errorResponse);
        }
    }

    @GetMapping("/resume/occupation/{occupation}")
    public ResponseEntity<Map<String,Object>> getResumeStatByOccupation(@RequestParam("occupation") String occupation){
        try {
            Map<String, Object> response=adminService.OccupResumeCnt(occupation);
            return ResponseEntity.ok().body(response);
        }catch(Exception e){
            Map<String,Object> errorResponse=new HashMap<>();
            errorResponse.put("status","Fail");
            errorResponse.put("time",new Date());
            errorResponse.put("response", "서버 오류입니다.");
            return ResponseEntity.internalServerError().body(errorResponse);
        }
    }
}
