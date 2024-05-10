package com.team2.resumeeditorproject.resume.controller;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.team2.resumeeditorproject.resume.domain.Resume;
import com.team2.resumeeditorproject.resume.dto.ResumeBoardDTO;
import com.team2.resumeeditorproject.resume.dto.ResumeDTO;
import com.team2.resumeeditorproject.resume.service.ResumeBoardService;
import com.team2.resumeeditorproject.resume.service.ResumeEditService;
import com.team2.resumeeditorproject.resume.dto.ResumeEditDTO;
import com.team2.resumeeditorproject.resume.service.ResumeService;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
/**
 * resumeEditController
 *
 * @author : 안은비
 * @fileName : ResumeEditController
 * @since : 04/25/24
 */
@RestController
@RequestMapping("/resumeEdit")
public class ResumeEditController {
    @Autowired
    private ResumeEditService resumeEditService;

    @Autowired
    private ResumeService resumeService;

    @Autowired
    private ResumeBoardService resumeBoardService;

    @PostMapping("/upload")
    public ResponseEntity<Map<String, String>> insertResumeEdit(@RequestBody Map<String, Object> requestBody) {
        Map<String, String> response = new HashMap<>();
        Date today = new Date();
        try{
            // 요청 본문에서 content 필드 추출
            String content = (String) requestBody.get("content");
            // content를 제외한 나머지 데이터를 ResumeEditDTO로 매핑
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            ResumeEditDTO resumeEditDTO = objectMapper.convertValue(requestBody, ResumeEditDTO.class);
            resumeEditDTO = resumeEditService.insertResumeEdit(resumeEditDTO);

            Long resumeEditId = resumeEditDTO.getR_num(); // resumeEdit 테이블의 primary key 얻기
            int mode = resumeEditDTO.getMode(); // mode 얻기

            // resume 테이블에 저장
            ResumeDTO resumeDTO = new ResumeDTO();
            resumeDTO.setR_num(resumeEditId);
            resumeDTO.setContent(content);
            resumeDTO.setU_num(resumeEditDTO.getU_num());
            resumeDTO = resumeService.insertResume(resumeDTO);
            Long resumeId = resumeDTO.getR_num();

            String resMsg = "resumeEdit/resume table insert success";

            // mode가 pro(2)인 경우, resume_board 테이블에 저장
            if(mode==2) {
                ResumeBoardDTO resumeBoardDTO = new ResumeBoardDTO();
                String title = resumeEditDTO.getCompany() + " " + resumeEditDTO.getOccupation();
                resumeBoardDTO.setTitle(title);
                resumeBoardDTO.setRNum(resumeId);
                resumeBoardDTO.setRating(0);
                resumeBoardDTO.setRating_count(0);
                resumeBoardDTO.setRead_num(0);
                resumeBoardService.insertResumeBoard(resumeBoardDTO);
                resMsg += ", resume_board table insert success";
            }

            response.put("response", resMsg);
            response.put("time", today.toString());
            response.put("status", "Success");
            return ResponseEntity.ok(response);
        }
        catch (Exception e){
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("response", "server error");
            errorResponse.put("time", today.toString());
            errorResponse.put("status", "Fail");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }



/*
    @GetMapping("/resultContent/{resumeId}")
    public ResponseEntity<String> getResumeContent(@PathVariable("resumeId")  Long resumeId) {
        try {
            // resumeId를 사용하여 resume 테이블에서 content 내용을 가져옴
            String content = resumeService.getResumeContent(resumeId);

            // content 내용을 클라이언트에게 반환
            return ResponseEntity.ok(content);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to fetch resume content");
        }
    }
*/

}
