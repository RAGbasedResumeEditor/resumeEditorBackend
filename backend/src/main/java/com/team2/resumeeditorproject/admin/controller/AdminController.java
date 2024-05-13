package com.team2.resumeeditorproject.admin.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.team2.resumeeditorproject.admin.service.AdminService;
import com.team2.resumeeditorproject.user.dto.UserDTO;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;

    @GetMapping("/stat/user/count")
    public ResponseEntity<Map<String,Object>> getUserCnt(){
        Map<String, Object> response = new HashMap<>();
        Map<String,Object> errorResponse=new HashMap<>();
        Integer userCnt=null;
        try {
            userCnt=adminService.userCnt();
            response.put("status", "Success");
            response.put("time", new Date());
            response.put("response", "총 유저는 "+userCnt+"명입니다.");
            return ResponseEntity.ok().body(response);
        }catch(Exception e){
            errorResponse.put("status","Fail");
            errorResponse.put("time",new Date());
            errorResponse.put("response", "서버 오류입니다.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @GetMapping("/stat/user/gender")
    public ResponseEntity<Map<String,Object>> getUserGender(){
        Map<String, Object> response = new HashMap<>();
        Map<String, Object> errorResponse=new HashMap<>();
        try {
            List<String> genderCnt=adminService.genderCnt();
            response.put("status", "Success");
            response.put("time", new Date());
            response.put("response", "여성은 "+genderCnt.get(0)+"%, 남성은 "+genderCnt.get(1)+"% 입니다.");
            return ResponseEntity.ok().body(response);
        }catch(Exception e){
            errorResponse.put("status","Fail");
            errorResponse.put("time",new Date());
            errorResponse.put("response", "서버 오류입니다.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @GetMapping("/stat/user/occupation")
    public ResponseEntity<Map<String,Object>> getUserOccupation(HttpServletRequest req){
        UserDTO userDto=new UserDTO();
        try{
            ObjectMapper objectMapper=new ObjectMapper();
            ServletInputStream inputStream=req.getInputStream();
            String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
            userDto=objectMapper.readValue(messageBody, UserDTO.class);
        }catch(IOException e){
            throw new RuntimeException(e);
        }
        String occupation=userDto.getOccupation();

        Map<String, Object> response = new HashMap<>();
        Map<String,Object> errorResponse=new HashMap<>();
        try {
            System.out.println(occupation);
            String occupCnt=adminService.occupCnt(occupation);
            response.put("status", "Success");
            response.put("time", new Date());
            response.put("response", occupation+" "+occupCnt+"%");
            return ResponseEntity.ok().body(response);
        }catch(Exception e){
            errorResponse.put("status","Fail");
            errorResponse.put("time",new Date());
            errorResponse.put("response", "서버 오류입니다.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @GetMapping("/stat/user/age")
    public ResponseEntity<Map<String,Object>> getUserAge(){
        Map<String,Object> response = new HashMap<>();
        Map<String,Object> errorResponse=new HashMap<>();
        try {
            Map<String, Integer> ageCnt=adminService.ageCnt();
            response.put("status", "Success");
            response.put("time", new Date());
            response.put("response", ageCnt);
            return ResponseEntity.ok().body(response);
        }catch(Exception e){
            errorResponse.put("status","Fail");
            errorResponse.put("time",new Date());
            errorResponse.put("response", "서버 오류입니다.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @GetMapping("/stat/user/wish")
    public ResponseEntity<Map<String,Object>> getUserWish(HttpServletRequest req){
        UserDTO userDto=new UserDTO();
        try{
            ObjectMapper objectMapper=new ObjectMapper();
            ServletInputStream inputStream=req.getInputStream();
            String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
            userDto=objectMapper.readValue(messageBody, UserDTO.class);
        }catch(IOException e){
            throw new RuntimeException(e);
        }
        String wish=userDto.getWish();

        Map<String,Object> response = new HashMap<>();
        Map<String,Object> errorResponse=new HashMap<>();
        try {
            String wishCnt=adminService.wishCnt(wish);
            response.put("status", "Success");
            response.put("time", new Date());
            response.put("response", wish+" "+wishCnt+"%");
            return ResponseEntity.ok().body(response);
        }catch(Exception e){
            errorResponse.put("status","Fail");
            errorResponse.put("time",new Date());
            errorResponse.put("response", "서버 오류입니다.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @GetMapping("/stat/user/status")
    public ResponseEntity<Map<String,Object>> getUserStatus(){
        Map<String,Object> response = new HashMap<>();
        Map<String,Object> errorResponse=new HashMap<>();
        try {
            Map<String, String> statusCnt=adminService.statusCnt();
            response.put("status", "Success");
            response.put("time", new Date());
            response.put("response", statusCnt);
            return ResponseEntity.ok().body(response);
        }catch(Exception e){
            errorResponse.put("status","Fail");
            errorResponse.put("time",new Date());
            errorResponse.put("response", "서버 오류입니다.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @GetMapping("/stat/user/mode")
    public ResponseEntity<Map<String,Object>> getUserMode(){
        Map<String,Object> response = new HashMap<>();
        Map<String,Object> errorResponse=new HashMap<>();
        try {
            Map<String, String> modeCnt=adminService.modeCnt();
            response.put("status", "Success");
            response.put("time", new Date());
            response.put("response", modeCnt);
            return ResponseEntity.ok().body(response);
        }catch(Exception e){
            errorResponse.put("status","Fail");
            errorResponse.put("time",new Date());
            errorResponse.put("response", "서버 오류입니다.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
}
