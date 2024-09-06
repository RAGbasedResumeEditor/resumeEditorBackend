package com.team2.resumeeditorproject.resume.controller;

import com.team2.resumeeditorproject.common.dto.response.ResultMessageResponse;
import com.team2.resumeeditorproject.resume.dto.request.ResumeEditRequest;
import com.team2.resumeeditorproject.resume.service.ResumeEditService;
import com.team2.resumeeditorproject.user.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
	public ResponseEntity<ResultMessageResponse> saveResumeEdit(@RequestBody ResumeEditRequest resumeEditRequest, UserDTO loginUser) {
		return ResponseEntity.ok()
				.body(ResultMessageResponse.builder()
						.response(resumeEditService.saveResumeEdit(resumeEditRequest, loginUser))
						.status("Success")
						.build());

	}
}
