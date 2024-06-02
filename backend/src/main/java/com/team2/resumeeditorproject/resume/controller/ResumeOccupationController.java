package com.team2.resumeeditorproject.resume.controller;

import com.team2.resumeeditorproject.resume.domain.Occupation;
import com.team2.resumeeditorproject.resume.repository.OccupationRepository;
import com.team2.resumeeditorproject.resume.service.OccupationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/resume-occupation")
public class ResumeOccupationController {
    @Autowired
    private OccupationService occupationService;

    @Autowired
    private OccupationRepository occupationRepository;

    @GetMapping("/load/{occupation}")
    public ResponseEntity<Map<String, Object>> loadOccupation(@PathVariable("occupation")  String occupation) {
        Map<String, Object> response = new HashMap<>();
        try {
            List<Occupation> existingOccupation = occupationRepository.findByOccupationContaining(occupation);
            if (existingOccupation != null && !existingOccupation.isEmpty()) {
                response.put("status","Success");
                response.put("occupationList", existingOccupation);

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