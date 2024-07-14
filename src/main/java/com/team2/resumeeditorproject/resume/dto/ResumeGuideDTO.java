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
    private Long resumeGuideNo;
    private Long userNo;
    private String company;
    private String occupation;
    private String content;
}
