package com.team2.resumeeditorproject.admin.controller;

import com.team2.resumeeditorproject.admin.service.ResumeManagementService;
import com.team2.resumeeditorproject.resume.domain.ResumeBoard;
import com.team2.resumeeditorproject.resume.dto.ResumeBoardDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class ResumeManagementController {

    private final ResumeManagementService rmService;

    //자소서 목록 가져오기
    @GetMapping("/resume/list/{page}")
    public ResponseEntity<Map<String, Object>> getAllResumeBoard(@PathVariable("page") int page){
        Page<ResumeBoard> resumeList=rmService.getResumeBoards(page);
        Map<String,Object> errorResponse=new HashMap<>();
        if(resumeList.isEmpty()){
            errorResponse.put("status","Fail");
            errorResponse.put("time",new Date());
            errorResponse.put("response", "자소서가 존재하지 않습니다.");
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
        response.put("totalPage",resumeList.getTotalPages());
        response.put("resumeList", resumeDtoList);
        return ResponseEntity.ok().body(response);
    }

    //자소서 삭제
    @PostMapping("/resume/delete/{rNum}")
    public ResponseEntity<Map<String, Object>> deleteResume(@PathVariable("rNum") long rNum){
        System.out.println(rNum);
        Map<String,Object> errorResponse1=new HashMap<>();
        Map<String,Object> errorResponse2=new HashMap<>();
        try {
            if(!rmService.checkResumeExists(rNum)){
                errorResponse1.put("status","Fail");
                errorResponse1.put("time",new Date());
                errorResponse1.put("response", "존재하지 않는 자소서입니다.");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse1);
            }
            rmService.deleteResume(rNum);
            Map<String, Object> response = new HashMap<>();
            response.put("status", "Success");
            response.put("time", new Date());
            response.put("response", rNum + "번 자소서 삭제 성공");
            return ResponseEntity.ok().body(response);
        }catch(Exception e){
            errorResponse2.put("status","Fail");
            errorResponse2.put("time",new Date());
            errorResponse2.put("response", "서버 오류입니다.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse2);
        }
    }
/*
    //자소서 수정
    @PostMapping("/resume/update")
    public ResponseEntity<Map<String, Object>> updateResume(@RequestBody ResumeBoardDTO rbDto){

        Map<String,Object> response=new HashMap<>();
        Map<String,Object> errorResponse1=new HashMap<>();
        Map<String,Object> errorResponse2=new HashMap<>();

        try{   //수정 처리
            if(!rmService.checkResumeExists(rbDto.getRNum())){
                errorResponse1.put("status","Fail");
                errorResponse1.put("time",new Date());
                errorResponse1.put("response", "존재하지 않는 자소서입니다.");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse1);
            }
            rmService.updateResume(rbDto);
            response.put("status","Success");
            response.put("time", new Date());
            response.put("response", rbDto.getRNum()+"번 자소서 수정 완료.");
            return ResponseEntity.ok(response);
        }catch(Exception e){
            errorResponse2.put("status","Fail");
            errorResponse2.put("time",new Date());
            errorResponse2.put("response", "서버 오류입니다.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse2);
        }
    }
*/
    //자소서 검색
    @GetMapping("/resume/search/title/{title}/{page}")
    public ResponseEntity<Map<String, Object>> searchTitle(@PathVariable("title") String title, @PathVariable("page") int page){
        Map<String,Object> response=new HashMap<>();
        Map<String,Object> errorResponse=new HashMap<>();
        try{
            Page<ResumeBoard> rbList=rmService.searchByTitle(title, page);
            response.put("totalPage",rbList.getTotalPages());
            response.put("response", rbList);
            return ResponseEntity.ok(response);
        }catch(Exception e){
            errorResponse.put("status","Fail");
            errorResponse.put("time",new Date());
            errorResponse.put("response", "서버 오류입니다.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @GetMapping("/resume/search/rating/{rating}/{page}")
    public ResponseEntity<Map<String, Object>> searchRating(@PathVariable("rating")Float rating, @PathVariable("page") int page){
        Map<String,Object> response=new HashMap<>();
        Map<String,Object> errorResponse=new HashMap<>();
        try {
            Page<ResumeBoard> rbList = rmService.searchByRating(rating, page);
            response.put("totalPage",rbList.getTotalPages());
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
