package com.team2.resumeeditorproject.company.service;

import java.util.List;

import com.team2.resumeeditorproject.company.dto.CompanyDTO;

public interface CompanyService {
	List<CompanyDTO> searchCompany(String keyword);
}
