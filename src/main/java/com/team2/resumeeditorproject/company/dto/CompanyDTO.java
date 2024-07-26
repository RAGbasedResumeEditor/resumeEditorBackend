package com.team2.resumeeditorproject.company.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CompanyDTO {
	private long companyNo;
	private String companyName;
	private String questions;
}
