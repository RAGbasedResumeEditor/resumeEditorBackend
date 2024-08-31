package com.team2.resumeeditorproject.gpt.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JobSearchResultDTO {
    private String 경력;        // Career
    private String 근무지역;     // Work Location
    private String 근무형태;     // Work Type (e.g., Full-time, Part-time)
    private String 급여;        // Salary
    private String 등록일자;     // Registration Date
    private String 임금형태;     // Wage Type (e.g., Annual Salary)
    private String 직종코드;     // Job Code
    private String 채용제목;     // Job Title
    private String 최소학력;     // Minimum Education Level
    private String 회사명;       // Company Name
}