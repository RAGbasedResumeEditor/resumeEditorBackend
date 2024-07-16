package com.team2.resumeeditorproject.resume.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResumeGuideDTO {
    @JsonProperty("resumeGuide_no")
    private Long resumeGuideNo;

    @JsonProperty("user_no")
    private Long userNo;

    @JsonProperty("company_no")
    private Long companyNo;

    @JsonProperty("company_name")
    private String companyName;

    @JsonProperty("occupation_no")
    private Long occupationNo;

    @JsonProperty("occupation_name")
    private String occupationName;

    private String content;

    private String questions;
    private int mode;
}
