package com.team2.resumeeditorproject.resume.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.team2.resumeeditorproject.common.dto.response.ResultMessageResponse;
import com.team2.resumeeditorproject.resume.dto.ResumeGuideDTO;
import com.team2.resumeeditorproject.resume.service.ResumeGuideService;
import com.team2.resumeeditorproject.user.dto.UserDTO;

@RestController
@RequestMapping("/resume/guide")
public class ResumeGuideController {

	@Autowired
	private ResumeGuideService resumeGuideService;

	@PostMapping
	public ResponseEntity<ResultMessageResponse> saveResumeGuide(@RequestBody ResumeGuideDTO resumeGuideDTO, UserDTO loginUser) {
		return ResponseEntity.ok()
				.body(ResultMessageResponse.builder()
						.response(resumeGuideService.saveResumeGuide(resumeGuideDTO, loginUser))
						.status("success")
						.build());
	}

}
