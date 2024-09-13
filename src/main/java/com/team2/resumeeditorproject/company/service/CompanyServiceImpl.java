package com.team2.resumeeditorproject.company.service;

import com.team2.resumeeditorproject.company.dto.CompanyDTO;
import com.team2.resumeeditorproject.company.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {

	private final CompanyRepository companyRepository;

	@Override
	public List<CompanyDTO> searchCompany(String keyword) {
		return companyRepository.findByCompanyNameContaining(keyword)
				.stream()
				.map(company -> CompanyDTO.builder()
						.companyNo(company.getCompanyNo())
						.companyName(company.getCompanyName())
						.questions(company.getQuestions())
						.build())
				.toList();
	}

	@Override
	public long findCompany(String companyName) {
		if(companyRepository.findByCompanyName(companyName)==null){
			return -1;
		}

		return companyRepository.findByCompanyName(companyName).getCompanyNo();
	}
}
