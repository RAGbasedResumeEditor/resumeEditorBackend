package com.team2.resumeeditorproject.company.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.team2.resumeeditorproject.common.dto.response.CommonListResponse;
import com.team2.resumeeditorproject.company.dto.CompanyDTO;
import com.team2.resumeeditorproject.company.service.CompanyService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/company")
@RequiredArgsConstructor
public class CompanyController {
	private final CompanyService companyService;

	@GetMapping("/search")
	public ResponseEntity<CommonListResponse<CompanyDTO>> loadItems(@RequestParam(name = "keyword") String keyword) {
		return ResponseEntity.ok()
				.body(CommonListResponse.<CompanyDTO>builder()
						.response(companyService.searchCompany(keyword))
						.status("Success")
						.build());
	}
}
