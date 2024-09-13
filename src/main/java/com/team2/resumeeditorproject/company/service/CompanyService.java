package com.team2.resumeeditorproject.company.service;

import com.team2.resumeeditorproject.company.dto.CompanyDTO;

import java.util.List;

public interface CompanyService {
	List<CompanyDTO> searchCompany(String keyword);

	long findCompany(String companyName);
}
