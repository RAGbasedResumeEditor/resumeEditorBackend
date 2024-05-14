package com.team2.resumeeditorproject.admin.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.team2.resumeeditorproject.admin.service.ResumeManagementService;
import com.team2.resumeeditorproject.resume.domain.ResumeBoard;
import com.team2.resumeeditorproject.resume.dto.ResumeBoardDTO;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
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

    private final ResumeManagementService rmService;

    //자소서 목록 가져오기
    @GetMapping("/resume/list")
    public ResponseEntity<Map<String, Object>> resumeList(){
        List<ResumeBoard> resumeList=rmService.getAllResume();

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
        response.put("자소서 게시글 목록", resumeDtoList);
        return ResponseEntity.ok().body(response);
    }

    //자소서 삭제
    @PostMapping("/resume/delete")
    public ResponseEntity<Map<String, Object>> deleteResume(@RequestBody ResumeBoardDTO rbDto){
        Map<String,Object> errorResponse1=new HashMap<>();
        Map<String,Object> errorResponse2=new HashMap<>();
        if(rbDto.getRNum()==null){
            errorResponse1.put("status","Fail");
            errorResponse1.put("time",new Date());
            errorResponse1.put("response", "존재하지 않는 자소서입니다.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse1);
        }
        try {
            rmService.deleteResume(rbDto);
            Map<String, Object> response = new HashMap<>();
            response.put("status", "Success");
            response.put("time", new Date());
            response.put("response", rbDto.getRNum() + "번 자소서 삭제 성공");
            return ResponseEntity.ok().body(response);
        }catch(Exception e){
            errorResponse1.put("status","Fail");
            errorResponse1.put("time",new Date());
            errorResponse1.put("response", "서버 오류입니다.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse2);
        }
    }

    //자소서 수정
    @PostMapping("resume/update")
    public ResponseEntity<Map<String, Object>> updateResume(@RequestBody ResumeBoardDTO rbDto){

        Map<String,Object> response=new HashMap<>();
        Map<String,Object> errorResponse1=new HashMap<>();
        Map<String,Object> errorResponse2=new HashMap<>();
        //유효성 검사
        if(rbDto.getRNum()==null){
            errorResponse1.put("status","Fail");
            errorResponse1.put("time",new Date());
            errorResponse1.put("response", "존재하지 않는 자소서입니다.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse1);
        }
        //수정 처리
        try{
            rmService.updateResume(rbDto);
            response.put("status","Success");
            response.put("time", new Date());
            response.put("response", rbDto.getRNum()+"번 자소서 수정 완료.");
            return ResponseEntity.ok(response);
        }catch(Exception e){
            errorResponse1.put("status","Fail");
            errorResponse1.put("time",new Date());
            errorResponse1.put("response", "서버 오류입니다.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse2);
        }
    }

    //자소서 검색
    @GetMapping("/resume/list/title")
    public ResponseEntity<Map<String, Object>> searchTitle(@RequestBody ResumeBoardDTO rbDto){
        Map<String,Object> response=new HashMap<>();
        Map<String,Object> errorResponse=new HashMap<>();
        try{
            List<ResumeBoard> rbList=rmService.searchByTitle(rbDto.getTitle(), 1, 10);
            response.put("status","Success");
            response.put("time", new Date());
            response.put("response", rbList);
            return ResponseEntity.ok(response);
        }catch(Exception e){
            errorResponse.put("status","Fail");
            errorResponse.put("time",new Date());
            errorResponse.put("response", "서버 오류입니다.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @GetMapping("/resume/list/rating")
    public ResponseEntity<Map<String, Object>> searchRating(HttpServletRequest req){
        ResumeBoardDTO rbDto=new ResumeBoardDTO();
        try{
            ObjectMapper objectMapper=new ObjectMapper();
            ServletInputStream inputStream=req.getInputStream();
            String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
            rbDto=objectMapper.readValue(messageBody, ResumeBoardDTO.class);
        }catch(IOException e){
            throw new RuntimeException(e);
        }
        Float rating=rbDto.getRating();

        Map<String,Object> response=new HashMap<>();
        Map<String,Object> errorResponse=new HashMap<>();
        try {
            List<ResumeBoard> rbList = rmService.searchByRating(rating, 1, 10);
            response.put("status", "Success");
            response.put("time", new Date());
            response.put("response", rbList);
            return ResponseEntity.ok(response);
        }catch(Exception e){
            errorResponse.put("status","Fail");
            errorResponse.put("time",new Date());
            errorResponse.put("response", "서버 오류입니다.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }


}
