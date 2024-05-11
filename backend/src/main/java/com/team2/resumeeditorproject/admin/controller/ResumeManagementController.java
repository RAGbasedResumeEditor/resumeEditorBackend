package com.team2.resumeeditorproject.admin.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.team2.resumeeditorproject.admin.repository.AdminResumeEditRepository;
import com.team2.resumeeditorproject.admin.service.AdminService;
import com.team2.resumeeditorproject.admin.service.UserManagementService;
import com.team2.resumeeditorproject.resume.domain.ResumeBoard;
import com.team2.resumeeditorproject.resume.dto.ResumeBoardDTO;
import com.team2.resumeeditorproject.user.dto.UserDTO;
import jakarta.servlet.ServletInputStream;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class ResumeManagementController {

    private final AdminService adminService;

    //자소서 목록 가져오기
    @GetMapping("/resume/list")
    public ResponseEntity<Map<String, Object>> resumeList(){
        List<ResumeBoard> resumeList=adminService.getAllResume();

        Map<String,Object> errorResponse=new HashMap<>();
        if(resumeList.isEmpty()){
            errorResponse.put("status","Fail");
            errorResponse.put("time",new Date());
            errorResponse.put("response", "존재하지 않는 자소서입니다.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }

        List<ResumeBoardDTO> resumeDtoList=new ArrayList<>();

        for(ResumeBoard rb:resumeList){
            ResumeBoardDTO rbDto=new ResumeBoardDTO();
            rbDto.setRating(rb.getRating());
            rbDto.setTitle(rb.getTitle());
            rbDto.setRNum(rb.getRNum());
            rbDto.setRead_num(rb.getRead_num());
            rbDto.setRating_count(rb.getRating_count());

            resumeDtoList.add(rbDto);
        }

        Map<String, Object> response = new HashMap<>();
        response.put("status", "Success");
        response.put("time", new Date());
        response.put("response", "자소서 목록 가져오기 성공");
        response.put("result", resumeDtoList);

        return ResponseEntity.ok().body(response);
    }

    //자소서 삭제
    @GetMapping("/resume/delete")
    public ResponseEntity<Map<String, Object>> deleteResume(@RequestBody ResumeBoardDTO rbDto){
        Map<String,Object> errorResponse=new HashMap<>();
        if(rbDto.getRNum()==null){
            errorResponse.put("status","Fail");
            errorResponse.put("time",new Date());
            errorResponse.put("response", "저장한 자소서가 없습니다.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
        adminService.deleteResume(rbDto);
        Map<String, Object> response = new HashMap<>();
        response.put("status", "Success");
        response.put("time", new Date());
        response.put("response", rbDto.getRNum()+"번 자소서 삭제 성공");

        return ResponseEntity.ok().body(response);
    }

    //자소서 수정
    @PostMapping("resume/update")
    public ResponseEntity<Map<String, Object>> updateResume(@RequestBody ResumeBoardDTO rbDto){

        Map<String,Object> response=new HashMap<>();
        Map<String,Object> errorResponse=new HashMap<>();
        //유효성 검사
        if(rbDto.getRNum()==null){
            errorResponse.put("status","Fail");
            errorResponse.put("time",new Date());
            errorResponse.put("response", "존재하지 않는 자소서입니다.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
        //수정 처리
        adminService.updateResume(rbDto);
        response.put("status","Success");
        response.put("time", new Date());
        response.put("response", rbDto.getRNum()+"번 자소서 수정 완료.");
        return ResponseEntity.ok(response);
    }
}
