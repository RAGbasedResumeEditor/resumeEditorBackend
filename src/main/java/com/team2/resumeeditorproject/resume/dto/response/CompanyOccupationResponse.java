package com.team2.resumeeditorproject.resume.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class CompanyOccupationResponse {
    private long companyNo;
    private long occupationNo;
    private String status;
}
