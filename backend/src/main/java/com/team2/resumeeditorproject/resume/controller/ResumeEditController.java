package com.team2.resumeeditorproject.resume.controller;

import com.team2.resumeeditorproject.resume.service.ResumeEditService;
import com.team2.resumeeditorproject.resume.dto.ResumeEditDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/resumeEdit")
public class ResumeEditController {
    @Autowired
    private ResumeEditService resumeEditService;

    @PostMapping
    public ResponseEntity<ResumeEditDTO> insertResume(@RequestBody ResumeEditDTO resumeEditDTO) {
        System.out.println(resumeEditDTO.toString());
        ResumeEditDTO dto = resumeEditService.insertResume(resumeEditDTO);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }
}
