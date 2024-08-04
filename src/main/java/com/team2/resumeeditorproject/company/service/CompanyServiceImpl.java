package com.team2.resumeeditorproject.company.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.team2.resumeeditorproject.company.dto.CompanyDTO;
import com.team2.resumeeditorproject.company.repository.CompanyRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {

	private final CompanyRepository companyRepository;

	@Override
	public List<CompanyDTO> searchCompany(String keyword) {
		return companyRepository.findTop5ByCompanyNameContaining(keyword)
				.stream()
				.map(company -> CompanyDTO.builder()
						.companyNo(company.getCompanyNo())
						.companyName(company.getCompanyName())
						.questions(company.getQuestions())
						.build())
				.toList();
	}
}
