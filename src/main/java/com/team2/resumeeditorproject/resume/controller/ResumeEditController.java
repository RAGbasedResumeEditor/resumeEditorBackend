package com.team2.resumeeditorproject.resume.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.team2.resumeeditorproject.common.dto.response.ResultMessageResponse;
import com.team2.resumeeditorproject.resume.dto.ResumeEditDTO;
import com.team2.resumeeditorproject.resume.service.ResumeEditService;
import com.team2.resumeeditorproject.user.dto.UserDTO;

/**
 * resumeEditController
 *
 * @author : 안은비
 * @fileName : ResumeEditController
 * @since : 04/25/24
 */
@RestController
@RequestMapping("/resume-edit")
public class ResumeEditController {
	@Autowired
	private ResumeEditService resumeEditService;

	@PostMapping
	public ResponseEntity<ResultMessageResponse> saveResumeEdit(@RequestBody ResumeEditDTO resumeEditDTO, UserDTO loginUser) {
		return ResponseEntity.ok()
				.body(ResultMessageResponse.builder()
						.response(resumeEditService.saveResumeEdit(resumeEditDTO, loginUser))
						.status("Success")
						.build());

	}
}
