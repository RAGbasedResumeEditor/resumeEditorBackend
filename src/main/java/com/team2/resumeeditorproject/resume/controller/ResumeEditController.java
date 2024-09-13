package com.team2.resumeeditorproject.resume.controller;

import com.team2.resumeeditorproject.common.dto.response.ResultMessageResponse;
import com.team2.resumeeditorproject.company.service.CompanyService;
import com.team2.resumeeditorproject.occupation.service.OccupationService;
import com.team2.resumeeditorproject.resume.dto.request.ResumeEditRequest;
import com.team2.resumeeditorproject.resume.dto.response.CompanyOccupationResponse;
import com.team2.resumeeditorproject.resume.service.ResumeEditService;
import com.team2.resumeeditorproject.user.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

	@Autowired
	private CompanyService companyService;

	@Autowired
	private OccupationService occupationService;

	@PostMapping
	public ResponseEntity<ResultMessageResponse> saveResumeEdit(@RequestBody ResumeEditRequest resumeEditRequest, UserDTO loginUser) {
		return ResponseEntity.ok()
				.body(ResultMessageResponse.builder()
						.response(resumeEditService.saveResumeEdit(resumeEditRequest, loginUser))
						.status("Success")
						.build());

	}

	@GetMapping("/search")
	public ResponseEntity<CompanyOccupationResponse> searchCompanyAndOccupation(@RequestParam("companyName") String companyName, @RequestParam("occupationName") String occupationName) {
		return ResponseEntity.ok()
				.body(CompanyOccupationResponse.builder()
						.companyNo(companyService.findCompany(companyName))
						.occupationNo(occupationService.findOccupation(occupationName))
						.status("Success")
						.build());

	}
}
