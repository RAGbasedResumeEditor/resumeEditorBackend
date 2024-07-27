package com.team2.resumeeditorproject.occupation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.team2.resumeeditorproject.common.dto.response.CommonListResponse;
import com.team2.resumeeditorproject.occupation.dto.OccupationDTO;
import com.team2.resumeeditorproject.occupation.service.OccupationService;

@RestController
@RequestMapping("/occupation")
public class OccupationController {
	@Autowired
	private OccupationService occupationService;

	@GetMapping("/search")
	public ResponseEntity<CommonListResponse<OccupationDTO>> loadOccupation(@RequestParam(name = "keyword") String keyword) {
		return ResponseEntity.ok()
				.body(CommonListResponse.<OccupationDTO>builder()
						.response(occupationService.searchOccupations(keyword))
						.status("Success")
						.build());
	}
}
