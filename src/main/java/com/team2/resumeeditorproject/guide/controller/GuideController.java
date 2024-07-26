package com.team2.resumeeditorproject.guide.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.team2.resumeeditorproject.common.dto.response.CommonResponse;
import com.team2.resumeeditorproject.common.dto.response.ResultMessageResponse;
import com.team2.resumeeditorproject.guide.dto.GuideDTO;
import com.team2.resumeeditorproject.guide.service.GuideService;
import com.team2.resumeeditorproject.user.dto.UserDTO;

@RestController
@RequestMapping("/guide")
public class GuideController {

	@Autowired
	private GuideService guideService;

	@PostMapping
	public ResponseEntity<ResultMessageResponse> saveGuide(@RequestBody GuideDTO guideDTO, UserDTO loginUser) {
		return ResponseEntity
				.ok()
				.body(ResultMessageResponse.builder()
						.response(guideService.saveGuide(guideDTO, loginUser))
						.status("success")
						.build());
	}

	@GetMapping()
	public ResponseEntity<CommonResponse<GuideDTO>> loadGuide(UserDTO loginUser) {
		return ResponseEntity
				.ok()
				.body(CommonResponse.<GuideDTO>builder()
						.response(guideService.getGuide(loginUser))
						.status("success")
						.build());
	}
}
