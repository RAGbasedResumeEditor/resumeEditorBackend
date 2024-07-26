package com.team2.resumeeditorproject.resume.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.team2.resumeeditorproject.common.dto.response.CommonResponse;
import com.team2.resumeeditorproject.exception.DataNotFoundException;
import com.team2.resumeeditorproject.resume.dto.ResumeRatingDTO;
import com.team2.resumeeditorproject.resume.service.ResumeRatingService;
import com.team2.resumeeditorproject.resume.service.ResumeBoardService;
import com.team2.resumeeditorproject.user.dto.UserDTO;

@RestController
@RequestMapping("/board")
public class ResumeRatingController {

	@Autowired
	private ResumeBoardService resumeBoardService;

	@Autowired
	private ResumeRatingService resumeRatingService;

	/* 별점 확인 */
	@GetMapping("/{resumeBoardNo}/rating")
	public ResponseEntity<CommonResponse<ResumeRatingDTO>> getRating(@PathVariable("resumeBoardNo") long resumeBoardNo, UserDTO loginUser) {
		if (resumeBoardService.isNotExistResumeBoard(resumeBoardNo)) {
			throw new DataNotFoundException("ResumeBoard with num " + resumeBoardNo + " not found");
		}

		return ResponseEntity.ok()
				.body(CommonResponse.<ResumeRatingDTO>builder()
						.response(resumeRatingService.getResumeRating(resumeBoardNo, loginUser))
						.status("success")
						.build());
	}

}
