package com.team2.resumeeditorproject.resume.controller;

import com.team2.resumeeditorproject.resume.domain.Guide;
import com.team2.resumeeditorproject.resume.dto.GuideDTO;
import com.team2.resumeeditorproject.resume.repository.GuideRepository;
import com.team2.resumeeditorproject.resume.service.GuideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/resume-guide")
public class ResumeGuideController {
    @Autowired
    private GuideService guideService;

    @Autowired
    private GuideRepository guideRepository;

    @PostMapping("/upload")
    public ResponseEntity<Map<String, String>> insertGuide(@RequestBody GuideDTO guideDto) {
        Map<String, String> response = new HashMap<>();
        try {
            // 요청 본문에서 uNum, awards, experiences 필드 추출ResumeGuideController
            long uNum = guideDto.getUNum();
            String awards = guideDto.getAwards();
            String experiences = guideDto.getExperiences();
            // uNum으로 엔티티 검색
            Guide existingGuide = guideRepository.findByUNum(uNum);
            if (existingGuide != null) {
                // 기존 데이터와 동일한지 확인
                if (!existingGuide.getAwards().equals(awards) || !existingGuide.getExperiences().equals(experiences)) {
                    // 데이터가 다르면 업데이트
                    existingGuide.setUNum(uNum);
                    existingGuide.setAwards(awards);
                    existingGuide.setExperiences(experiences);
                    guideRepository.save(existingGuide);
                    response.put("status", "Updated");
                } else {
                    response.put("status", "No changes made");
                }
            } else {
                // 기존 데이터가 없으면 새로 삽입
                System.out.println(uNum);
                GuideDTO guideDTO = new GuideDTO(uNum, awards, experiences);
                guideService.insertGuide(guideDTO);
                response.put("status", "Inserted");
            }
        } catch (Exception e) {
            response.put("status", "Error");
            response.put("message", e.getMessage());
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping("/load/{num}")
    public ResponseEntity<Map<String, Object>> loadGuide(@PathVariable("num")  Long num) {
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