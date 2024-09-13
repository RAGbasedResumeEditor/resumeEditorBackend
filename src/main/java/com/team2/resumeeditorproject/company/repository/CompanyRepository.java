package com.team2.resumeeditorproject.company.repository;

import com.team2.resumeeditorproject.company.domain.Company;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CompanyRepository extends JpaRepository<Company, Long> {
    List<Company> findByCompanyNameContaining(String company);

    Company findByCompanyName(String companyName);
}
