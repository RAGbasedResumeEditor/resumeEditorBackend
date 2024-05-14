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
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
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
        Map<String,Object> response = new HashMap<>();
        Map<String,Object> errorResponse=new HashMap<>();
        try {
            response=adminService.userCnt();
            return ResponseEntity.ok().body(response);
        }catch(Exception e){
            errorResponse.put("status","Fail");
            errorResponse.put("time",new Date());
            errorResponse.put("response", "서버 오류입니다.");
            return ResponseEntity.internalServerError().body(errorResponse);
        }
    }

    @GetMapping("/user/gender")
    public ResponseEntity<Map<String,Object>> getUserGender(){
        Map<String, Object> errorResponse=new HashMap<>();
        try {
            Map<String, Object> response=adminService.genderCnt();
            return ResponseEntity.ok().body(response);
        }catch(Exception e){
            errorResponse.put("status","Fail");
            errorResponse.put("time",new Date());
            errorResponse.put("response", "서버 오류입니다.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @GetMapping("/user/occupation")
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

        Map<String,Object> response = new HashMap<>();
        Map<String,Object> errorResponse=new HashMap<>();
        try {
            response=adminService.occupCnt(occupation);
            return ResponseEntity.ok().body(response);
        }catch(Exception e){
            errorResponse.put("status","Fail");
            errorResponse.put("time",new Date());
            errorResponse.put("response", "서버 오류입니다.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @GetMapping("/user/age")
    public ResponseEntity<Map<String,Object>> getUserAge(){
        Map<String,Object> response = new HashMap<>();
        Map<String,Object> errorResponse=new HashMap<>();
        try {
            Map<String, Integer> ageCnt=adminService.ageCnt();
            response.put("response", ageCnt);
            return ResponseEntity.ok().body(response);
        }catch(Exception e){
            errorResponse.put("status","Fail");
            errorResponse.put("time",new Date());
            errorResponse.put("response", "서버 오류입니다.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @GetMapping("/user/wish")
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
            response=adminService.wishCnt(wish);
            return ResponseEntity.ok().body(response);
        }catch(Exception e){
            errorResponse.put("status","Fail");
            errorResponse.put("time",new Date());
            errorResponse.put("response", "서버 오류입니다.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @GetMapping("/user/status")
    public ResponseEntity<Map<String,Object>> getUserStatus(){
        Map<String,Object> response = new HashMap<>();
        Map<String,Object> errorResponse=new HashMap<>();
        try {
            response=adminService.statusCnt();
            return ResponseEntity.ok().body(response);
        }catch(Exception e){
            errorResponse.put("status","Fail");
            errorResponse.put("time",new Date());
            errorResponse.put("response", "서버 오류입니다.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @GetMapping("/user/mode")
    public ResponseEntity<Map<String,Object>> getUserMode(){
        Map<String,Object> response = new HashMap<>();
        Map<String,Object> errorResponse=new HashMap<>();
        try {
            Map<String, String> modeCnt=adminService.modeCnt();
            response.put("response", modeCnt);
            return ResponseEntity.ok().body(response);
        }catch(Exception e){
            errorResponse.put("status","Fail");
            errorResponse.put("time",new Date());
            errorResponse.put("response", "서버 오류입니다.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @GetMapping("/resume/company")
    public ResponseEntity<Map<String,Object>> getResumeStatByCompany(HttpServletRequest req){
        UserDTO userDto=new UserDTO();
        try{
            ObjectMapper objectMapper=new ObjectMapper();
            ServletInputStream inputStream=req.getInputStream();
            String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
            userDto=objectMapper.readValue(messageBody, UserDTO.class);
        }catch(IOException e){
            throw new RuntimeException(e);
        }
        String company=userDto.getCompany();
        Map<String,Object> errorResponse=new HashMap<>();

        try {
            Map<String, Object> response=adminService.CompResumeCnt(company);
            return ResponseEntity.ok().body(response);
        }catch(Exception e){
            errorResponse.put("status","Fail");
            errorResponse.put("time",new Date());
            errorResponse.put("response", "서버 오류입니다.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @GetMapping("/resume/occupation")
    public ResponseEntity<Map<String,Object>> getResumeStatByOccupation(HttpServletRequest req){
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
        Map<String,Object> errorResponse=new HashMap<>();

        try {
            Map<String, Object> response=adminService.OccupResumeCnt(occupation);
            return ResponseEntity.ok().body(response);
        }catch(Exception e){
            errorResponse.put("status","Fail");
            errorResponse.put("time",new Date());
            errorResponse.put("response", "서버 오류입니다.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
}
