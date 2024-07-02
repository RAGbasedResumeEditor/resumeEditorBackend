package com.team2.resumeeditorproject.resume.controller;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.team2.resumeeditorproject.resume.domain.Guide;
import com.team2.resumeeditorproject.resume.dto.GuideDTO;
import com.team2.resumeeditorproject.resume.dto.ResumeGuideDTO;
import com.team2.resumeeditorproject.resume.repository.GuideRepository;
import com.team2.resumeeditorproject.resume.service.GuideService;
import com.team2.resumeeditorproject.resume.service.ResumeGuideService;
import com.team2.resumeeditorproject.user.domain.User;
import com.team2.resumeeditorproject.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/resume-guide")
public class ResumeGuideController {
    @Autowired
    private GuideService guideService;

    @Autowired
    private GuideRepository guideRepository;

    @Autowired
    private ResumeGuideService resumeGuideService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/upload")
    public ResponseEntity<Map<String, String>> insertData(@RequestBody Map<String, Object> requestBody) {
        Map<String, String> response = new HashMap<>();
        Date today = new Date();

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

            boolean guideSaved = false;
            boolean resumeGuideSaved = false;

            // GuideDTO 처리
            if (requestBody.containsKey("uNum") && requestBody.containsKey("awards") && requestBody.containsKey("experiences")) {
                GuideDTO guideDto = objectMapper.convertValue(requestBody, GuideDTO.class);

                Long uNum = ((Number) requestBody.get("uNum")).longValue(); // Integer -> Long 변환
                String awards = guideDto.getAwards();
                String experiences = guideDto.getExperiences();

                // User 테이블에서 해당하는 uNum을 가진 사용자가 존재하는지 확인
                Optional<User> userOptional = userRepository.findById(uNum);
                if (userOptional.isPresent()) {
                    // 새로운 데이터를 추가하여 저장
                    Guide existingGuide = guideRepository.findByUNum(uNum);
                    if (existingGuide != null) {
                        // 기존 데이터와 동일한지 확인
                        if (!existingGuide.getAwards().equals(awards) || !existingGuide.getExperiences().equals(experiences)) {
                            // 데이터가 다르면 업데이트
                            existingGuide.setUNum(uNum);
                            existingGuide.setAwards(awards);
                            existingGuide.setExperiences(experiences);
                            guideRepository.save(existingGuide);
                            response.put("status_guide", "Updated Guide");
                        } else {
                            response.put("status_guide", "No changes made");
                        }
                    } else {
                        // 기존 데이터가 없으면 새로 삽입
                        GuideDTO newGuideDTO = new GuideDTO(uNum, awards, experiences);
                        guideService.insertGuide(newGuideDTO);
                        response.put("status_guide", "Inserted Guide");
                    }
                    guideSaved = true; // Guide 데이터 저장 여부 설정
                } else {
                    response.put("status", "User does not exist");
                }
            }

            // ResumeGuideDTO 처리
            if (requestBody.containsKey("uNum") && requestBody.containsKey("company") && requestBody.containsKey("occupation") && requestBody.containsKey("content")) {
                ResumeGuideDTO resumeGuideDTO = objectMapper.convertValue(requestBody, ResumeGuideDTO.class);

                Long uNum = ((Number) requestBody.get("uNum")).longValue(); // Integer -> Long 변환
                String company = resumeGuideDTO.getCompany();
                String occupation = resumeGuideDTO.getOccupation();
                String content = resumeGuideDTO.getContent();

                // User 테이블에서 해당하는 uNum을 가진 사용자가 존재하는지 확인
                Optional<User> userOptional = userRepository.findById(uNum);
                if (userOptional.isPresent()) {
                    // 새로운 데이터를 추가하여 저장
                    resumeGuideDTO.setUNum(uNum);
                    ResumeGuideDTO savedResumeGuide = resumeGuideService.saveResumeGuide(resumeGuideDTO);
                    response.put("status_resumeGuide", "Inserted ResumeGuide");
                    resumeGuideSaved = true; // ResumeGuide 데이터 저장 여부 설정
                } else {
                    response.put("status", "User does not exist");
                }
            }

            // Guide와 ResumeGuide 모두 저장된 경우 메시지 설정
            if (guideSaved && resumeGuideSaved) {
                response.put("response", "Guide and ResumeGuide table insert success");
                response.put("status", "Success");
            }
        } catch (Exception e) {
            response.put("status", "Fail");
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
        response.put("time", today.toString());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/load/{num}")
    public ResponseEntity<Map<String, Object>> loadGuide(@PathVariable("num") Long num) {
        Map<String, Object> response = new HashMap<>();
        try {
            Guide existingGuide = guideRepository.findByUNum(num);
            if (existingGuide != null) {
                response.put("status","Success");
                response.put("uNum", existingGuide.getUNum());
                response.put("awards", existingGuide.getAwards());
                response.put("experiences", existingGuide.getExperiences());
            } else {
                response.put("status", "Not found");
            }
        } catch (Exception e) {
            response.put("status", "Error");
            response.put("message", e.getMessage());
        }
        return ResponseEntity.ok(response);
    }
}